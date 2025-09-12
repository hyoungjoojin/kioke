package io.kioke.feature.auth.controller;

import io.kioke.exception.user.UserAlreadyExistsException;
import io.kioke.feature.auth.dto.request.SignInRequestDto;
import io.kioke.feature.auth.dto.request.SignUpRequestDto;
import io.kioke.feature.auth.service.AuthService;
import io.kioke.feature.auth.service.SessionService;
import io.kioke.feature.dashboard.service.DashboardService;
import io.kioke.feature.preferences.service.PreferencesService;
import io.kioke.feature.profile.service.ProfileService;
import io.kioke.feature.user.dto.UserDto;
import io.kioke.feature.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  private final UserService userService;
  private final AuthService authService;
  private final SessionService sessionService;
  private final ProfileService profileService;
  private final PreferencesService preferencesService;
  private final DashboardService dashboardService;

  public AuthController(
      UserService userService,
      AuthService authService,
      SessionService sessionService,
      PreferencesService preferencesService,
      ProfileService profileService,
      DashboardService dashboardService) {
    this.userService = userService;
    this.authService = authService;
    this.sessionService = sessionService;
    this.profileService = profileService;
    this.preferencesService = preferencesService;
    this.dashboardService = dashboardService;
  }

  @PostMapping("/auth/signin")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void signIn(
      HttpServletRequest request, @RequestBody @Validated SignInRequestDto requestBody) {
    Authentication authentication =
        authService.authenticate(requestBody.email(), requestBody.password());

    sessionService.createSession(request, authentication);
  }

  @PostMapping("/auth/signup")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void signUp(@RequestBody @Validated SignUpRequestDto requestBody)
      throws UserAlreadyExistsException {
    UserDto user = userService.createUser(requestBody.email());
    authService.createUserDetails(user, requestBody.password());
    profileService.createProfile(user);
    preferencesService.createPreferences(user);
    dashboardService.createDashboard(user);
  }

  @PostMapping("/auth/signout")
  public void signOut(HttpServletRequest request) {
    sessionService.destroySession(request);
  }
}
