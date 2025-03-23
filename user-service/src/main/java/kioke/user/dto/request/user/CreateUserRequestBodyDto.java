package kioke.user.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequestBodyDto {
  @NotNull private final String uid;
  @NotNull private final String email;
  @NotNull private final String firstName;
  @NotNull private final String lastName;
}
