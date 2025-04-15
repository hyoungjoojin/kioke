package kioke.user.dto.response.user;

import kioke.user.model.User;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetUserResponseBodyDto(String email, String firstName, String lastName) {

  public static GetUserResponseBodyDto from(User user) {
    return GetUserResponseBodyDto.builder()
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .build();
  }
}
