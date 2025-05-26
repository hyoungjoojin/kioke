package kioke.user.feature.user.dto.data;

import kioke.user.feature.user.domain.User;

public record UserDto(String userId, String email, String name) {

  public static UserDto from(User user) {
    return new UserDto(user.getUserId(), user.getEmail(), user.getName());
  }
}
