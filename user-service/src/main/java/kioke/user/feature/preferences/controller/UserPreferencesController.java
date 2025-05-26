package kioke.user.feature.preferences.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kioke.commons.annotation.HttpResponse;
import kioke.user.feature.preferences.dto.data.UserPreferencesDto;
import kioke.user.feature.preferences.dto.response.GetUserPreferencesResponseBodyDto;
import kioke.user.feature.preferences.service.UserPreferencesService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User Preferences API", description = "API endpoints for user preferences management")
public class UserPreferencesController {

  private UserPreferencesService userPreferencesService;

  public UserPreferencesController(UserPreferencesService userPreferencesService) {
    this.userPreferencesService = userPreferencesService;
  }

  @GetMapping("/users/preferences")
  @HttpResponse(status = HttpStatus.OK)
  @Operation(
      summary = "Get User Preferences",
      description = "Get the user's preferences for the authenticated user.")
  public GetUserPreferencesResponseBodyDto getUserPreferences(
      @AuthenticationPrincipal String userId) {
    UserPreferencesDto userPreferencesDto =
        userPreferencesService.getUserPreferencesByUserId(userId);

    return GetUserPreferencesResponseBodyDto.from(userPreferencesDto);
  }
}
