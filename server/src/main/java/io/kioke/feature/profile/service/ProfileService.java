package io.kioke.feature.profile.service;

import io.kioke.feature.profile.domain.Profile;
import io.kioke.feature.profile.dto.ProfileDto;
import io.kioke.feature.profile.dto.request.UpdateProfileRequestDto;
import io.kioke.feature.profile.repository.ProfileRepository;
import io.kioke.feature.profile.util.ProfileMapper;
import io.kioke.feature.user.dto.UserDto;
import io.kioke.feature.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ProfileService {

  private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

  private final UserService userService;
  private final ProfileRepository profileRepository;
  private final ProfileMapper profileMapper;

  public ProfileService(
      UserService userService, ProfileRepository profileRepository, ProfileMapper profileMapper) {
    this.userService = userService;
    this.profileRepository = profileRepository;
    this.profileMapper = profileMapper;
  }

  @Transactional
  public Profile createProfile(UserDto user) {
    Assert.notNull(user, "User must not be null");
    Profile profile = Profile.from(user.userId());

    profile = profileRepository.save(profile);
    logger.debug("Profile for user {} has been created", user.userId());
    return profile;
  }

  @Transactional(readOnly = true)
  public ProfileDto getProfile(UserDto user) {
    Assert.notNull(user, "User must not be null");

    Profile profile =
        profileRepository
            .findById(user.userId())
            .orElseThrow(() -> new IllegalStateException("User profile does not exist"));

    String email =
        userService
            .findById(user.userId())
            .orElseThrow(() -> new IllegalStateException("User profile does not exist"))
            .getEmail();

    return profileMapper.toDto(profile, email);
  }

  @Transactional
  public void updateProfile(UserDto user, UpdateProfileRequestDto request) {
    Profile profile =
        profileRepository
            .findById(user.userId())
            .orElseThrow(() -> new IllegalStateException("User profile does not exist"));

    if (request.name() != null) {
      profile.changeName(request.name());
    }

    if (request.onboarded() != null) {
      profile.setOnboardingStatus(request.onboarded());
    }

    profileRepository.save(profile);
    logger.debug("Profile for user {} has been updated", user.userId());
  }
}
