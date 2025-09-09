package io.kioke.feature.friend.dto.response;

import java.util.List;

public record GetFriendsResponseDto(List<Friend> friends) {

  public static record Friend(String userId) {}
}
