package com.kioke.user.dto.response.data.friend;

import com.kioke.user.model.User;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetFriendsResponseBodyDto {
  private List<FriendDto> friends;

  @Data
  @Builder
  private static class FriendDto {
    private String uid;
    private String email;
    private String firstName;
    private String lastName;

    public static FriendDto from(User friend) {
      return FriendDto.builder()
          .uid(friend.getUid())
          .email(friend.getEmail())
          .firstName(friend.getFirstName())
          .lastName(friend.getLastName())
          .build();
    }
  }

  public static GetFriendsResponseBodyDto from(List<User> friends) {
    return GetFriendsResponseBodyDto.builder()
        .friends(friends.stream().map(friend -> FriendDto.from(friend)).toList())
        .build();
  }
}
