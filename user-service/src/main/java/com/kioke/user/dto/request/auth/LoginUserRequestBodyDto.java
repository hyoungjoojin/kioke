package com.kioke.user.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserRequestBodyDto {
  @NotNull private final String email;
  @NotNull private final String password;
}
