package com.kioke.journal.controller;

import com.kioke.journal.dto.request.user.CreateUserRequestBodyDto;
import com.kioke.journal.model.User;
import com.kioke.journal.service.ShelfService;
import com.kioke.journal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired @Lazy UserService userService;
  @Autowired @Lazy ShelfService shelfService;

  @PostMapping
  public ResponseEntity<Void> createUser(
      @RequestBody @Valid CreateUserRequestBodyDto requestBodyDto) {
    String uid = requestBodyDto.getUid();
    User user = userService.createUser(uid);

    shelfService.createArchive(user);
    shelfService.createShelf(user, "My Bookshelf");

    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }
}
