package io.kioke.feature.preferences.controller;

import io.kioke.annotation.AuthenticatedUser;
import io.kioke.feature.preferences.dto.request.UpdatePreferencesRequest;
import io.kioke.feature.preferences.service.PreferencesService;
import io.kioke.feature.user.dto.UserDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreferencesController {

  private final PreferencesService preferencesService;

  public PreferencesController(PreferencesService preferencesService) {
    this.preferencesService = preferencesService;
  }

  @PatchMapping("/preferences")
  public void updatePreferences(
      @AuthenticatedUser UserDto user,
      @RequestBody @Validated UpdatePreferencesRequest requestBody) {
    preferencesService.updatePreferences(user, requestBody);
  }
}
