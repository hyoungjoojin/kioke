package io.kioke.feature.profile.controller;

import io.kioke.annotation.AuthenticatedUser;
import io.kioke.exception.user.UserNotFoundException;
import io.kioke.feature.preferences.dto.PreferencesDto;
import io.kioke.feature.preferences.service.PreferencesService;
import io.kioke.feature.profile.dto.ProfileDto;
import io.kioke.feature.profile.dto.request.UpdateProfileRequestDto;
import io.kioke.feature.profile.dto.response.GetMyProfileResponse;
import io.kioke.feature.profile.dto.response.SearchProfilesResponseDto;
import io.kioke.feature.profile.service.ProfileService;
import io.kioke.feature.profile.util.ProfileMapper;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  private final ProfileService profileService;
  private final PreferencesService preferencesService;
  private final ProfileMapper profileMapper;

  public ProfileController(
      ProfileService profileService,
      PreferencesService preferencesService,
      ProfileMapper profileMapper) {
    this.profileService = profileService;
    this.preferencesService = preferencesService;
    this.profileMapper = profileMapper;
  }

  @GetMapping("/users/me")
  @ResponseStatus(HttpStatus.OK)
  public GetMyProfileResponse getMyProfile(@AuthenticatedUser UserDto user)
      throws UserNotFoundException {
    ProfileDto profile = profileService.getProfile(user);
    PreferencesDto preferences = preferencesService.getPreferences(user);
    return profileMapper.toGetMyProfileResponse(profile, preferences);
  }

  @GetMapping("/users/search")
  @ResponseStatus(HttpStatus.OK)
  public SearchProfilesResponseDto searchProfiles(
      @AuthenticatedUser UserDto user, @RequestParam(required = true) String query) {
    List<ProfileDto> profiles = profileService.searchProfiles(query);
    return profileMapper.toSearchProfilesResponse(query, profiles);
  }

  @PatchMapping("/users/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateProfile(
      @AuthenticatedUser UserDto user, @RequestBody @Validated UpdateProfileRequestDto requestDto)
      throws UserNotFoundException {
    profileService.updateProfile(user, requestDto);
  }
}
