package io.kioke.feature.collection.controller;

import io.kioke.common.auth.AuthenticatedUser;
import io.kioke.exception.collection.CollectionNotFoundException;
import io.kioke.feature.collection.dto.CollectionDto;
import io.kioke.feature.collection.dto.CreateCollectionRequest;
import io.kioke.feature.collection.service.CollectionService;
import io.kioke.feature.user.dto.UserPrincipal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CollectionController {

  private final CollectionService collectionService;

  @GetMapping("/collections/{collectionId}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public CollectionDto getCollectionById(@PathVariable String collectionId)
      throws CollectionNotFoundException {
    return collectionService.getCollectionById(collectionId);
  }

  @GetMapping("/collections")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public List<CollectionDto> getCollections(@AuthenticatedUser UserPrincipal user) {
    return collectionService.getCollections(user);
  }

  @PostMapping("/collections")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("isAuthenticated()")
  public CollectionDto createCollection(
      @AuthenticatedUser UserPrincipal user,
      @RequestBody @Validated CreateCollectionRequest requestBody) {
    return collectionService.createCollection(user, requestBody);
  }

  @DeleteMapping("/collections/{collectionId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("isAuthenticated()")
  public void deleteCollection(@PathVariable String collectionId) {
    collectionService.deleteCollection(collectionId);
  }
}
