package com.kioke.user.dto.response.data.auth;

import com.kioke.user.dto.response.data.ResponseDataDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserResponseDataDto implements ResponseDataDto {
  private final String token;
}
