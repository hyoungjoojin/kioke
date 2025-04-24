package kioke.journal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kioke.commons.annotation.HttpResponse;
import kioke.journal.dto.request.shelf.CreateShelfRequestBodyDto;
import kioke.journal.dto.request.shelf.UpdateShelfRequestBodyDto;
import kioke.journal.dto.response.shelf.CreateShelfResponseBodyDto;
import kioke.journal.dto.response.shelf.GetShelfResponseBodyDto;
import kioke.journal.exception.shelf.ShelfNotFoundException;
import kioke.journal.model.Shelf;
import kioke.journal.service.BookmarkService;
import kioke.journal.service.ShelfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shelves")
@Slf4j
public class ShelfController {

  private final ShelfService shelfService;
  private final BookmarkService bookmarkService;

  public ShelfController(ShelfService shelfService, BookmarkService bookmarkService) {
    this.shelfService = shelfService;
    this.bookmarkService = bookmarkService;
  }

  @GetMapping
  @Tag(name = "Get Shelves")
  @Operation(summary = "Get all shelves for a given user.")
  @HttpResponse(status = HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public List<GetShelfResponseBodyDto> getShelves(@AuthenticationPrincipal String userId) {
    List<Shelf> shelves = shelfService.getShelves(userId);
    List<String> bookmarks = bookmarkService.getBookmarkedJournalIds(userId);
    return GetShelfResponseBodyDto.from(shelves, bookmarks);
  }

  @GetMapping("/{shelfId}")
  @Tag(name = "Get Shelf By ID")
  @Operation(summary = "Get information about the shelf with the shelf ID.")
  @HttpResponse(status = HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public GetShelfResponseBodyDto getShelfById(
      @AuthenticationPrincipal String userId, @PathVariable String shelfId)
      throws ShelfNotFoundException {
    Shelf shelf = shelfService.getShelfById(userId, shelfId);
    List<String> bookmarks = bookmarkService.getBookmarkedJournalIds(userId);
    return GetShelfResponseBodyDto.from(shelf, bookmarks);
  }

  @PostMapping
  @HttpResponse(status = HttpStatus.CREATED)
  @PreAuthorize("isAuthenticated()")
  public CreateShelfResponseBodyDto createShelf(
      @AuthenticationPrincipal String userId,
      @RequestBody @Valid CreateShelfRequestBodyDto requestBodyDto) {
    Shelf shelf = shelfService.createShelf(userId, requestBodyDto.name());
    return CreateShelfResponseBodyDto.from(shelf);
  }

  @PatchMapping("/{shelfId}")
  @HttpResponse(status = HttpStatus.OK)
  public void updateShelf(
      @AuthenticationPrincipal String userId,
      @PathVariable String shelfId,
      @RequestBody @Valid UpdateShelfRequestBodyDto requestBodyDto)
      throws ShelfNotFoundException {
    shelfService.updateShelf(userId, shelfId, requestBodyDto.name());
  }
}
