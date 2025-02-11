package com.kioke.journal.controller;

import com.kioke.journal.dto.request.shelf.CreateShelfRequestBodyDto;
import com.kioke.journal.dto.response.shelf.CreateShelfResponseBodyDto;
import com.kioke.journal.exception.user.UserNotFoundException;
import com.kioke.journal.model.Shelf;
import com.kioke.journal.model.User;
import com.kioke.journal.service.ShelfService;
import com.kioke.journal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shelves")
public class ShelfController {
  @Autowired @Lazy private ShelfService shelfService;
  @Autowired @Lazy private UserService userService;

  @PostMapping
  public ResponseEntity<CreateShelfResponseBodyDto> createShelf(
      @RequestAttribute(required = true, name = "uid") String uid,
      @RequestBody @Valid CreateShelfRequestBodyDto requestBodyDto)
      throws UserNotFoundException {
    String name = requestBodyDto.getName();

    User user = userService.getUserById(uid);

    Shelf shelf = shelfService.createShelf(user, name);

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(CreateShelfResponseBodyDto.from(shelf));
  }
}
