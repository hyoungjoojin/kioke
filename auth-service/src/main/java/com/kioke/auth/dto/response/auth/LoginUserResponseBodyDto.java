package com.kioke.auth.dto.response.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserResponseBodyDto {
  private final String uid;
  private final String accessToken;
}
