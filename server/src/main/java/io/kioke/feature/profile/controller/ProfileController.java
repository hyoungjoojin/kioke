package io.kioke.feature.profile.controller;

import io.kioke.common.auth.AuthenticatedUser;
import io.kioke.exception.user.UserNotFoundException;
import io.kioke.feature.profile.dto.projection.MyProfileProjection;
import io.kioke.feature.profile.dto.request.UpdateProfileRequest;
import io.kioke.feature.profile.dto.response.ProfileResponse;
import io.kioke.feature.profile.service.ProfileService;
import io.kioke.feature.profile.util.ProfileMapper;
import io.kioke.feature.user.dto.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  private final ProfileService profileService;
  private final ProfileMapper profileMapper;

  public ProfileController(ProfileService profileService, ProfileMapper profileMapper) {
    this.profileService = profileService;
    this.profileMapper = profileMapper;
  }

  @GetMapping("/users/me")
  @ResponseStatus(HttpStatus.OK)
  public ProfileResponse getMyProfile(@AuthenticatedUser UserPrincipal user)
      throws UserNotFoundException {
    MyProfileProjection profile = profileService.getMyProfile(user.userId());
    return profileMapper.mapToProfileResponse(profile);
  }

  @PatchMapping("/users/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateProfile(
      @AuthenticatedUser UserPrincipal user,
      @RequestBody @Validated UpdateProfileRequest requestDto)
      throws UserNotFoundException {
    profileService.updateProfile(user.userId(), requestDto);
  }
}
