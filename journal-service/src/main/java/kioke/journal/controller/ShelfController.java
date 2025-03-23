package kioke.journal.controller;

import jakarta.validation.Valid;
import java.util.List;
import kioke.journal.dto.request.shelf.CreateShelfRequestBodyDto;
import kioke.journal.dto.response.shelf.CreateShelfResponseBodyDto;
import kioke.journal.dto.response.shelf.GetShelvesResponseBodyDto;
import kioke.journal.exception.user.UserNotFoundException;
import kioke.journal.model.Shelf;
import kioke.journal.model.User;
import kioke.journal.service.ShelfService;
import kioke.journal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
