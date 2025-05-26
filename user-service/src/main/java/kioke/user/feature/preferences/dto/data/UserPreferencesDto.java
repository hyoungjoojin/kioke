package kioke.user.feature.preferences.dto.data;

import kioke.user.feature.preferences.domain.Theme;
import kioke.user.feature.preferences.domain.UserPreferences;

public record UserPreferencesDto(String theme) {

  public UserPreferencesDto(Theme theme) {
    this(theme.toString());
  }

  public static UserPreferencesDto from(UserPreferences userPreferences) {
    return new UserPreferencesDto(userPreferences.getTheme().toString());
  }
}
