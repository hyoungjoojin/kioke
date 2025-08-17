package io.kioke.feature.journal.controller;

import io.kioke.annotation.AuthenticatedUser;
import io.kioke.exception.journal.JournalCollectionNotFoundException;
import io.kioke.feature.journal.dto.JournalCollectionDto;
import io.kioke.feature.journal.dto.request.CreateJournalCollectionRequestDto;
import io.kioke.feature.journal.dto.response.CreateJournalCollectionResponseDto;
import io.kioke.feature.journal.dto.response.GetJournalCollectionResponseDto;
import io.kioke.feature.journal.dto.response.GetJournalCollectionsResponseDto;
import io.kioke.feature.journal.service.JournalCollectionService;
import io.kioke.feature.journal.util.JournalMapper;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JournalCollectionController {

  private final JournalCollectionService journalCollectionService;

  private final JournalMapper journalMapper;

  public JournalCollectionController(
      JournalCollectionService journalCollectionService, JournalMapper journalMapper) {
    this.journalCollectionService = journalCollectionService;
    this.journalMapper = journalMapper;
  }

  @GetMapping("/collections/{collectionId}")
  @ResponseStatus(HttpStatus.OK)
  public GetJournalCollectionResponseDto getJournalCollection(
      @AuthenticatedUser UserDto user, @PathVariable String collectionId)
      throws JournalCollectionNotFoundException {
    JournalCollectionDto collection =
        journalCollectionService.getJournalCollection(user, collectionId);

    return journalMapper.toGetJournalCollectionResponse(collection);
  }

  @GetMapping("/collections")
  @ResponseStatus(HttpStatus.OK)
  public GetJournalCollectionsResponseDto getJournalCollections(@AuthenticatedUser UserDto user) {
    List<JournalCollectionDto> collections = journalCollectionService.getJournalCollections(user);
    return journalMapper.toGetJournalCollectionsResponse(collections);
  }

  @PostMapping("/collections")
  @ResponseStatus(HttpStatus.CREATED)
  public CreateJournalCollectionResponseDto createJournalCollection(
      @AuthenticatedUser UserDto user,
      @RequestBody @Validated CreateJournalCollectionRequestDto requestBody) {
    JournalCollectionDto collection =
        journalCollectionService.createJournalCollection(user, requestBody.name());

    return new CreateJournalCollectionResponseDto(collection.collectionId());
  }
}
