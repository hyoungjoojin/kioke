package com.kioke.auth.dto.data.auth;

import com.kioke.auth.dto.request.auth.RegisterUserRequestBodyDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserDto {
  private final String email;
  private final String password;
  private final String firstName;
  private final String lastName;

  public static RegisterUserDto from(RegisterUserRequestBodyDto requestBodyDto) {
    return RegisterUserDto.builder()
        .email(requestBodyDto.getEmail())
        .password(requestBodyDto.getPassword())
        .firstName(requestBodyDto.getFirstName())
        .lastName(requestBodyDto.getLastName())
        .build();
  }
}
