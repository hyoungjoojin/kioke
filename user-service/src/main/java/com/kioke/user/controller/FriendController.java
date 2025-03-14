package com.kioke.user.controller;

import com.kioke.user.dto.request.friend.AddFriendRequestBodyDto;
import com.kioke.user.dto.response.data.friend.GetFriendsResponseBodyDto;
import com.kioke.user.exception.friend.CannotAddSelfAsFriendException;
import com.kioke.user.exception.friend.FriendRelationAlreadyExistsException;
import com.kioke.user.exception.friend.FriendRequestAlreadySentException;
import com.kioke.user.exception.user.UserDoesNotExistException;
import com.kioke.user.model.User;
import com.kioke.user.service.FriendService;
import com.kioke.user.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
  public ResponseEntity<Void> addFriend(
      @AuthenticationPrincipal String uid,
      @RequestBody @Valid AddFriendRequestBodyDto requestBodyDto)
      throws CannotAddSelfAsFriendException,
          UserDoesNotExistException,
          FriendRequestAlreadySentException,
          FriendRelationAlreadyExistsException {
    String friendUid = requestBodyDto.getFriendUid();
    if (uid.equals(friendUid)) {
      throw new CannotAddSelfAsFriendException();
    }

    User user = userService.getUserById(uid),
        friend = userService.getUserById(requestBodyDto.getFriendUid());

    friendService.addFriend(user, friend);

    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }

  @GetMapping
  public ResponseEntity<GetFriendsResponseBodyDto> getFriends(@AuthenticationPrincipal String uid)
      throws UserDoesNotExistException {
    User user = userService.getUserById(uid);

    List<User> friends = friendService.getFriends(user);

    return ResponseEntity.status(HttpStatus.OK).body(GetFriendsResponseBodyDto.from(friends));
  }
}
