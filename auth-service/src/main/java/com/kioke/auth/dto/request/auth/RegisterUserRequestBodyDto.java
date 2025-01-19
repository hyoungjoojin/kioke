package com.kioke.auth.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterUserRequestBodyDto {
  @NotNull private final String email;
  @NotNull private final String password;
  @NotNull private final String firstName;
  @NotNull private final String lastName;
}
