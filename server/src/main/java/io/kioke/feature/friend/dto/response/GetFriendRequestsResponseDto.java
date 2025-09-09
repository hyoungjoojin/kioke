package io.kioke.feature.friend.dto.response;

import java.util.List;

public record GetFriendRequestsResponseDto(List<FriendRequest> requests) {

  public static record FriendRequest() {}
}
