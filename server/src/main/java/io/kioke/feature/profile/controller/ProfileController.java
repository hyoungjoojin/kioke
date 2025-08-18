package io.kioke.feature.profile.controller;

import io.kioke.annotation.AuthenticatedUser;
import io.kioke.exception.user.UserNotFoundException;
import io.kioke.feature.profile.dto.ProfileDto;
import io.kioke.feature.profile.dto.request.UpdateProfileRequestDto;
import io.kioke.feature.profile.dto.response.GetMyProfileResponseDto;
import io.kioke.feature.profile.service.ProfileService;
import io.kioke.feature.profile.util.ProfileMapper;
import io.kioke.feature.user.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  public GetMyProfileResponseDto getMyProfile(@AuthenticatedUser UserDto user)
      throws UserNotFoundException {
    ProfileDto profile = profileService.getMyProfile(user);
    return profileMapper.toGetMyProfileResponse(profile);
  }

  @PutMapping("/users/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateProfile(
      @AuthenticatedUser UserDto user, @RequestBody @Validated UpdateProfileRequestDto requestDto)
      throws UserNotFoundException {
    profileService.updateProfile(user, requestDto);
  }
}
