package kioke.journal.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import kioke.commons.http.HttpResponseBody;
import kioke.journal.dto.request.shelf.CreateShelfRequestBodyDto;
import kioke.journal.dto.response.shelf.CreateShelfResponseBodyDto;
import kioke.journal.dto.response.shelf.GetShelvesResponseBodyDto;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
  public ResponseEntity<HttpResponseBody<CreateShelfResponseBodyDto>> createShelf(
      @AuthenticationPrincipal String uid,
      @RequestBody @Valid CreateShelfRequestBodyDto requestBodyDto,
      HttpServletRequest request)
      throws UsernameNotFoundException {
    String name = requestBodyDto.getName();

    User user = userService.getUserById(uid);

    Shelf shelf = shelfService.createShelf(user, name);

    HttpStatus status = HttpStatus.CREATED;
    CreateShelfResponseBodyDto data = CreateShelfResponseBodyDto.from(shelf);

    return ResponseEntity.status(status)
        .contentType(MediaType.APPLICATION_JSON)
        .body(HttpResponseBody.success(request, status, data));
  }

  @GetMapping
  public ResponseEntity<HttpResponseBody<GetShelvesResponseBodyDto>> getShelves(
      @AuthenticationPrincipal String uid, HttpServletRequest request)
      throws UsernameNotFoundException {
    User user = userService.getUserById(uid);

    List<Shelf> shelves = shelfService.getShelves(user);

    HttpStatus status = HttpStatus.OK;
    GetShelvesResponseBodyDto data = GetShelvesResponseBodyDto.from(shelves);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(HttpResponseBody.success(request, status, data));
  }
}
