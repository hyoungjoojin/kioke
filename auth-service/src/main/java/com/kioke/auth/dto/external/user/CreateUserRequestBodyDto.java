package com.kioke.auth.dto.external.user;

import lombok.Data;

@Data
public class CreateUserRequestBodyDto {
  private final String uid;
  private final String email;
  private final String firstName;
  private final String lastName;
}
