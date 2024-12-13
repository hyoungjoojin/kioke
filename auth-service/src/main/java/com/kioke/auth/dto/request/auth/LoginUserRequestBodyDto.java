package com.kioke.auth.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginUserRequestBodyDto {
  @NotNull private final String email;
  @NotNull private final String password;
}
