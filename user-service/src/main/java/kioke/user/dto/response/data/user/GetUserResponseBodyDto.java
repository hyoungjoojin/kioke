package kioke.user.dto.response.data.user;

import kioke.user.constant.Locale;
import kioke.user.constant.Theme;
import kioke.user.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

public abstract class GetUserResponseBodyDto {

  @Data
  @EqualsAndHashCode(callSuper = false)
  @Builder
  public static class GetAuthenticatedUserResponseBodyDto extends GetUserResponseBodyDto {
    private String email;
    private String firstName;
    private String lastName;
    private Preferences preferences;

    public static GetAuthenticatedUserResponseBodyDto from(User user) {
      var responseBodyDto =
          GetAuthenticatedUserResponseBodyDto.builder()
              .email(user.getEmail())
              .firstName(user.getFirstName())
              .lastName(user.getLastName())
              .build();

      var userPreferences = user.getPreferences();
      responseBodyDto.setPreferences(
          new Preferences(userPreferences.getLocale(), userPreferences.getTheme()));

      return responseBodyDto;
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = false)
  @Builder
  public static class GetUserByIdResponseBodyDto extends GetUserResponseBodyDto {
    private String email;
    private String firstName;
    private String lastName;

    public static GetUserByIdResponseBodyDto from(User user) {
      return GetUserByIdResponseBodyDto.builder()
          .email(user.getEmail())
          .firstName(user.getFirstName())
          .lastName(user.getLastName())
          .build();
    }
  }

  @Data
  private static class Preferences {
    private String locale;
    private String theme;

    public Preferences(Locale locale, Theme theme) {
      this.locale = locale.getCode();
      this.theme = theme.toString();
    }
  }
}
