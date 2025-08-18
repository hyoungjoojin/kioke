package io.kioke.feature.profile.service;

import io.kioke.exception.user.UserNotFoundException;
import io.kioke.feature.profile.domain.Profile;
import io.kioke.feature.profile.dto.ProfileDto;
import io.kioke.feature.profile.dto.request.UpdateProfileRequestDto;
import io.kioke.feature.profile.repository.ProfileRepository;
import io.kioke.feature.user.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ProfileService {

  private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

  private final ProfileRepository profileRepository;

  public ProfileService(ProfileRepository userProfileRepository) {
    this.profileRepository = userProfileRepository;
  }

  @Transactional(readOnly = true)
  public ProfileDto getMyProfile(UserDto user) throws UserNotFoundException {
    Assert.notNull(user, "User must not be null");

    return profileRepository
        .findByUserId(user.userId())
        .orElseThrow(
            () -> {
              logger.debug("User {} was not found", user.userId());
              return new UserNotFoundException();
            });
  }

  @Transactional
  public void createProfile(String userId) {
    Profile profile = new Profile();
    profile.setUserId(userId);

    profileRepository.save(profile);
    logger.debug("Profile for user {} has been created", userId);
  }

  @Transactional
  public void updateProfile(UserDto user, UpdateProfileRequestDto request)
      throws UserNotFoundException {
    Assert.notNull(user, "User must not be null");
    Profile profile =
        profileRepository
            .findById(user.userId())
            .orElseThrow(
                () -> {
                  logger.debug("User {} was not found", user.userId());
                  return new UserNotFoundException();
                });

    if (request.name() != null) {
      profile.setName(request.name());
    }

    if (request.onboarded() != null) {
      profile.setOnboarded(request.onboarded());
    }

    profileRepository.save(profile);
    logger.debug("Profile for user {} has been updated", user.userId());
  }
}
