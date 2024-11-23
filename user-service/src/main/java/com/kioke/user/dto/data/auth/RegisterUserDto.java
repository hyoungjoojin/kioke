package com.kioke.user.dto.data.auth;

import com.kioke.user.dto.request.auth.RegisterUserRequestBodyDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserDto {
  private final String email;
  private final String password;

  public static RegisterUserDto from(RegisterUserRequestBodyDto registerUserRequestBodyDto) {
    return RegisterUserDto.builder()
        .email(registerUserRequestBodyDto.getEmail())
        .password(registerUserRequestBodyDto.getPassword())
        .build();
  }
}
