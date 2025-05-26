package kioke.user.feature.user.dto.response;

import kioke.user.feature.user.dto.data.UserDto;

public record GetMyInformationResponseBodyDto(String userId, String email, String name) {

  public static GetMyInformationResponseBodyDto from(UserDto userDto) {
    return new GetMyInformationResponseBodyDto(userDto.userId(), userDto.email(), userDto.name());
  }
}
