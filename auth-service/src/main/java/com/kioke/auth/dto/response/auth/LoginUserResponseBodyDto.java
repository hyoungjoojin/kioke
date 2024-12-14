package com.kioke.auth.dto.response.auth;

import lombok.Data;

@Data
public class LoginUserResponseBodyDto {
  private final String accessToken;
}
