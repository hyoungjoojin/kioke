package kioke.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import kioke.commons.http.HttpResponseBody;
import kioke.user.dto.request.friend.AddFriendRequestBodyDto;
import kioke.user.dto.response.friend.GetFriendsResponseBodyDto;
import kioke.user.exception.friend.CannotAddSelfAsFriendException;
import kioke.user.exception.friend.FriendRelationAlreadyExistsException;
import kioke.user.exception.friend.FriendRequestAlreadySentException;
import kioke.user.model.User;
import kioke.user.service.FriendService;
import kioke.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friends")
public class FriendController {
  @Autowired @Lazy private UserService userService;
  @Autowired @Lazy private FriendService friendService;

  @PostMapping
  public ResponseEntity<HttpResponseBody<Void>> addFriend(
      @AuthenticationPrincipal String uid,
      @RequestBody @Valid AddFriendRequestBodyDto requestBodyDto,
      HttpServletRequest request)
      throws UsernameNotFoundException,
          CannotAddSelfAsFriendException,
          FriendRequestAlreadySentException,
          FriendRelationAlreadyExistsException {
    String friendUid = requestBodyDto.getFriendUid();
    if (uid.equals(friendUid)) {
      throw new CannotAddSelfAsFriendException();
    }

    User user = userService.getUserById(uid),
        friend = userService.getUserById(requestBodyDto.getFriendUid());

    friendService.addFriend(user, friend);

    HttpStatus status = HttpStatus.CREATED;
    return ResponseEntity.status(status).body(HttpResponseBody.success(request, status, null));
  }

  @GetMapping
  public ResponseEntity<HttpResponseBody<GetFriendsResponseBodyDto>> getFriends(
      @AuthenticationPrincipal String uid, HttpServletRequest request)
      throws UsernameNotFoundException {
    User user = userService.getUserById(uid);

    List<User> friends = friendService.getFriends(user);

    HttpStatus status = HttpStatus.OK;
    return ResponseEntity.status(status)
        .body(HttpResponseBody.success(request, status, GetFriendsResponseBodyDto.from(friends)));
  }
}
