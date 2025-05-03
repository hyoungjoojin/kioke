package kioke.journal.controller;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kioke.commons.annotation.HttpResponse;
import kioke.commons.exception.security.AccessDeniedException;
import kioke.journal.dto.data.journal.JournalDto;
import kioke.journal.dto.data.journal.JournalPreviewDto;
import kioke.journal.dto.request.journal.CreateJournalRequestBodyDto;
import kioke.journal.dto.request.journal.ShareJournalRequestBodyDto;
import kioke.journal.dto.request.journal.UnshareJournalRequestBodyDto;
import kioke.journal.dto.request.journal.UpdateJournalRequestBodyDto;
import kioke.journal.dto.response.journal.CreateJournalResponseBodyDto;
import kioke.journal.dto.response.journal.GetJournalResponseBodyDto;
import kioke.journal.dto.response.journal.GetJournalsResponseBodyDto;
import kioke.journal.exception.journal.JournalNotFoundException;
import kioke.journal.exception.shelf.ShelfNotFoundException;
import kioke.journal.model.Journal;
import kioke.journal.service.JournalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journals")
@Tag(name = "Journal Operations API", description = "API endpoints for managing journals.")
@Slf4j
public class JournalController {

  private final JournalService journalService;

  public JournalController(JournalService journalService) {
    this.journalService = journalService;
  }

  @Timed(value = "getJournals_timer")
  @GetMapping
  @HttpResponse(status = HttpStatus.OK)
  @Operation(
      summary = "Get a list of all journals.",
      description = "Return a list of journals for the authenticated user.")
  @PreAuthorize("isAuthenticated()")
  public GetJournalsResponseBodyDto getJournals(
      @AuthenticationPrincipal String userId,
      @Parameter(
              name = "bookmarked",
              description = "If true, only return bookmarked journals.",
              in = ParameterIn.QUERY,
              required = false)
          @RequestParam(name = "bookmarked", required = false, defaultValue = "false")
          Boolean findOnlyBookmarkedJournals) {
    log.debug(
        "received GET /journals request with params (bookmarked={})", findOnlyBookmarkedJournals);

    List<JournalPreviewDto> journalPreviewDtos =
        journalService.getJournals(userId, findOnlyBookmarkedJournals);

    return GetJournalsResponseBodyDto.from(journalPreviewDtos);
  }

  @GetMapping("/recent")
  @Tag(name = "Get Recently Viewed Journals")
  @Operation(summary = "Get recently viewed journals for a given user.")
  @HttpResponse(status = HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public GetJournalsResponseBodyDto getRecentlyViewedJournals(
      @AuthenticationPrincipal String userId) {
    List<JournalPreviewDto> journalPreviewDtos = journalService.getJournals(userId, false);

    return GetJournalsResponseBodyDto.from(journalPreviewDtos);
  }

  @GetMapping("/{journalId}")
  @Tag(name = "Get Journal By ID")
  @Operation(summary = "Get information about the journal with the journal ID.")
  @HttpResponse(status = HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public GetJournalResponseBodyDto getJournalById(
      @AuthenticationPrincipal String userId, @PathVariable String journalId)
      throws JournalNotFoundException {
    JournalDto journal = journalService.getJournalById(userId, journalId);

    return GetJournalResponseBodyDto.from(journal);
  }

  @PostMapping
  @Tag(name = "Create Journal")
  @Operation(summary = "Create a new journal.")
  @HttpResponse(status = HttpStatus.CREATED)
  @PreAuthorize("isAuthenticated()")
  public CreateJournalResponseBodyDto createJournal(
      @AuthenticationPrincipal String userId,
      @RequestBody @Valid CreateJournalRequestBodyDto requestBodyDto)
      throws ShelfNotFoundException {
    Journal journal =
        journalService.createJournal(
            userId, requestBodyDto.shelfId(), requestBodyDto.title(), requestBodyDto.description());

    return CreateJournalResponseBodyDto.from(journal);
  }

  @PostMapping("/{journalId}/share")
  @Tag(name = "Share Journal")
  @Operation(summary = "Share a journal with another user.")
  @HttpResponse(status = HttpStatus.CREATED)
  @PreAuthorize("isAuthenticated()")
  public void shareJournal(
      @AuthenticationPrincipal String userId,
      @PathVariable String journalId,
      @RequestBody @Valid ShareJournalRequestBodyDto requestBodyDto)
      throws JournalNotFoundException, AccessDeniedException {
    journalService.shareJournal(userId, journalId, requestBodyDto.userId(), requestBodyDto.role());
  }

  @PatchMapping("/{journalId}")
  @Tag(name = "Update Journal By ID")
  @Operation(summary = "Update information about the journal with the journal ID.")
  @HttpResponse(status = HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public void updateJournal(
      @AuthenticationPrincipal String userId,
      @PathVariable String journalId,
      @RequestBody UpdateJournalRequestBodyDto requestBodyDto)
      throws JournalNotFoundException, AccessDeniedException, ShelfNotFoundException {
    journalService.updateJournal(userId, journalId, requestBodyDto);
  }

  @DeleteMapping("/{journalId}")
  @Tag(name = "Delete Journal By ID")
  @Operation(summary = "Delete the journal with the journal ID.")
  @HttpResponse(status = HttpStatus.NO_CONTENT)
  @PreAuthorize("isAuthenticated()")
  public void deleteJournal(@AuthenticationPrincipal String userId, @PathVariable String journalId)
      throws JournalNotFoundException, AccessDeniedException {
    journalService.deleteJournal(userId, journalId);
  }

  @DeleteMapping("/{journalId}/share")
  @Tag(name = "Unshare Journal")
  @Operation(summary = "Unshare a journal with another user.")
  @HttpResponse(status = HttpStatus.NO_CONTENT)
  @PreAuthorize("isAuthenticated()")
  public void unshareJournal(
      @AuthenticationPrincipal String userId,
      @PathVariable String journalId,
      @RequestBody @Valid UnshareJournalRequestBodyDto requestBodyDto)
      throws JournalNotFoundException, AccessDeniedException {
    journalService.unshareJournal(userId, journalId, requestBodyDto.userId());
  }
}
