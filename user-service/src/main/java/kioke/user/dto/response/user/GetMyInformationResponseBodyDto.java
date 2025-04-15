package kioke.user.dto.response.user;

import kioke.user.constant.Locale;
import kioke.user.constant.Theme;
import kioke.user.model.User;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetMyInformationResponseBodyDto(
    String userId, String email, String firstName, String lastName, Preferences preferences) {

  public static GetMyInformationResponseBodyDto from(User user) {
    return GetMyInformationResponseBodyDto.builder()
        .userId(user.getUid())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .preferences(
            new Preferences(user.getPreferences().getLocale(), user.getPreferences().getTheme()))
        .build();
  }

  private static record Preferences(String locale, String theme) {

    protected Preferences(Locale locale, Theme theme) {
      this(locale.getCode(), theme.toString());
    }
  }
}
