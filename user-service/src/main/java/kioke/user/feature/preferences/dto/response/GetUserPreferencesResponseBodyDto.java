package kioke.user.feature.preferences.dto.response;

import kioke.user.feature.preferences.dto.data.UserPreferencesDto;

public record GetUserPreferencesResponseBodyDto(String theme) {

  public static GetUserPreferencesResponseBodyDto from(UserPreferencesDto userPreferencesDto) {
    return new GetUserPreferencesResponseBodyDto(userPreferencesDto.theme());
  }
}
