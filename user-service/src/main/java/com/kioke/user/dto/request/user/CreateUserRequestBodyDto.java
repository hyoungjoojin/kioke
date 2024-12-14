package com.kioke.user.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequestBodyDto {
  @NotNull private final String uid;
  @NotNull private final String email;
}
