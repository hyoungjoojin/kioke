package com.kioke.journal.controller;

import com.kioke.journal.dto.request.shelf.CreateShelfRequestBodyDto;
import com.kioke.journal.dto.request.shelf.UpdateShelfRequestBodyDto;
import com.kioke.journal.dto.response.shelf.CreateShelfResponseBodyDto;
import com.kioke.journal.dto.response.shelf.GetShelvesResponseBodyDto;
import com.kioke.journal.exception.permission.AccessDeniedException;
import com.kioke.journal.exception.shelf.ShelfNotFoundException;
import com.kioke.journal.exception.user.UserNotFoundException;
import com.kioke.journal.model.Shelf;
import com.kioke.journal.model.User;
import com.kioke.journal.service.ShelfService;
import com.kioke.journal.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
  @Autowired @Lazy private ShelfService shelfService;
  @Autowired @Lazy private UserService userService;

  @PostMapping
  public ResponseEntity<CreateShelfResponseBodyDto> createShelf(
      @AuthenticationPrincipal String uid,
      @RequestBody @Valid CreateShelfRequestBodyDto requestBodyDto)
      throws UserNotFoundException {
    String name = requestBodyDto.getName();

    User user = userService.getUserById(uid);

    Shelf shelf = shelfService.createShelf(user, name);

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(CreateShelfResponseBodyDto.from(shelf));
  }

  @PatchMapping("/{shelfId}")
  public ResponseEntity<Void> updateShelf(
      @AuthenticationPrincipal String uid,
      @PathVariable String shelfId,
      @RequestBody @Valid UpdateShelfRequestBodyDto requestBodyDto)
      throws UserNotFoundException, ShelfNotFoundException, AccessDeniedException {
    User user = userService.getUserById(uid);

    Shelf shelf = shelfService.getShelfById(shelfId);
    if (!shelf.getOwner().equals(user)) {
      throw new AccessDeniedException();
    }

    shelfService.updateShelf(shelf, requestBodyDto.getName());

    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(null);
  }

  @GetMapping
  public ResponseEntity<GetShelvesResponseBodyDto> getShelves(@AuthenticationPrincipal String uid)
      throws UserNotFoundException {
    User user = userService.getUserById(uid);

    List<Shelf> shelves = shelfService.getShelves(user);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(GetShelvesResponseBodyDto.from(shelves));
  }
}
