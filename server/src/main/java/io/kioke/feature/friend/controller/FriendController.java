package io.kioke.feature.friend.controller;

import io.kioke.annotation.AuthenticatedUser;
import io.kioke.exception.friend.FriendshipAlreadyExistsException;
import io.kioke.feature.friend.dto.FriendDto;
import io.kioke.feature.friend.dto.request.SendFriendRequestDto;
import io.kioke.feature.friend.dto.response.GetFriendsResponseDto;
import io.kioke.feature.friend.service.FriendService;
import io.kioke.feature.friend.util.FriendMapper;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendController {

  private final FriendService friendService;
  private final FriendMapper friendMapper;

  public FriendController(FriendService friendService, FriendMapper friendMapper) {
    this.friendService = friendService;
    this.friendMapper = friendMapper;
  }

  @PostMapping("/friends")
  @ResponseStatus(HttpStatus.CREATED)
  public void sendFriendRequest(
      @AuthenticatedUser UserDto user, @RequestBody @Validated SendFriendRequestDto requestBody)
      throws FriendshipAlreadyExistsException {
    friendService.sendFriendRequest(user, requestBody);
  }

  @GetMapping("/friends")
  @ResponseStatus(HttpStatus.OK)
  public GetFriendsResponseDto getFriends(@AuthenticatedUser UserDto user) {
    List<FriendDto> friends = friendService.getFriends(user);
    return friendMapper.toGetFriendsResponse(friends.size(), friends);
  }
}
