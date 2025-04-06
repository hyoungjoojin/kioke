package kioke.user.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Optional;
import kioke.user.model.User;
import lombok.Builder;

@JsonInclude(Include.NON_EMPTY)
@Builder
public record SearchUserResponseBodyDto(
    String userId, String email, String firstName, String lastName) {

  public static SearchUserResponseBodyDto from(Optional<User> user) {
    if (user.isEmpty()) {
      return new SearchUserResponseBodyDto("", "", "", "");
    }

    User u = user.get();
    return SearchUserResponseBodyDto.builder()
        .userId(u.getUid())
        .email(u.getEmail())
        .firstName(u.getFirstName())
        .lastName(u.getLastName())
        .build();
  }
}
