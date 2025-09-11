package io.kioke.feature.collection.controller;

import io.kioke.annotation.AuthenticatedUser;
import io.kioke.exception.collection.CollectionNotFoundException;
import io.kioke.feature.collection.dto.CollectionDto;
import io.kioke.feature.collection.dto.request.CreateCollectionRequestDto;
import io.kioke.feature.collection.dto.response.CreateCollectionResponseDto;
import io.kioke.feature.collection.dto.response.GetCollectionResponseDto;
import io.kioke.feature.collection.dto.response.GetCollectionsResponseDto;
import io.kioke.feature.collection.service.CollectionService;
import io.kioke.feature.collection.util.CollectionMapper;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollectionController {

  private final CollectionService collectionService;
  private final CollectionMapper collectionMapper;

  public CollectionController(
      CollectionService collectionService, CollectionMapper collectionMapper) {
    this.collectionService = collectionService;
    this.collectionMapper = collectionMapper;
  }

  @PostMapping("/collections")
  @ResponseStatus(HttpStatus.CREATED)
  public CreateCollectionResponseDto createCollection(
      @AuthenticatedUser UserDto user,
      @RequestBody @Validated CreateCollectionRequestDto requestBody) {
    CollectionDto collection = collectionService.createCollection(user, requestBody);
    return collectionMapper.toCreateCollectionResponse(collection);
  }

  @GetMapping("/collections")
  @ResponseStatus(HttpStatus.OK)
  public GetCollectionsResponseDto getCollections(@AuthenticatedUser UserDto user) {
    List<CollectionDto> collections = collectionService.getCollections(user);
    return collectionMapper.toGetCollectionsResponse(collections.size(), collections);
  }

  @GetMapping("/collections/{collectionId}")
  @ResponseStatus(HttpStatus.OK)
  public GetCollectionResponseDto getCollection(
      @AuthenticatedUser UserDto user, @PathVariable String collectionId)
      throws CollectionNotFoundException {
    CollectionDto collection = collectionService.getCollection(user, collectionId);
    return collectionMapper.toGetCollectionResponse(collection);
  }

  @DeleteMapping("/collections/{collectionId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCollection(@AuthenticatedUser UserDto user, @PathVariable String collectionId)
      throws CollectionNotFoundException {
    collectionService.deleteCollection(user, collectionId);
  }
}
