package com.kioke.user.dto.data.user;

import com.kioke.user.dto.request.user.CreateUserRequestBodyDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserDto {
  private final String uid;
  private final String email;
  private final String firstName;
  private final String lastName;

  public static CreateUserDto from(CreateUserRequestBodyDto requestBodyDto) {
    return CreateUserDto.builder()
        .uid(requestBodyDto.getUid())
        .email(requestBodyDto.getEmail())
        .firstName(requestBodyDto.getFirstName())
        .lastName(requestBodyDto.getLastName())
        .build();
  }
}
