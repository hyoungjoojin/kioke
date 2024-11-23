package com.kioke.user.dto.response.data.auth;

import com.kioke.user.dto.response.data.ResponseDataDto;
import com.kioke.user.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserResponseDataDto implements ResponseDataDto {
  private final String uid;

  public static RegisterUserResponseDataDto from(User user) {
    return RegisterUserResponseDataDto.builder().uid(user.getId()).build();
  }
}
