package io.kioke.feature.profile.service;

import io.kioke.feature.profile.domain.Profile;
import io.kioke.feature.profile.dto.projection.MyProfileProjection;
import io.kioke.feature.profile.dto.request.UpdateProfileRequest;
import io.kioke.feature.profile.repository.ProfileRepository;
import io.kioke.feature.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

  private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

  private final ProfileRepository profileRepository;

  public ProfileService(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }

  @Transactional(readOnly = true)
  @PreAuthorize("#userId == authentication.principal")
  public MyProfileProjection getMyProfile(String userId) {
    return profileRepository
        .findMyProfileByUserId(userId)
        .orElseThrow(() -> new IllegalStateException("User profile does not exist"));
  }

  @Transactional
  public Profile createProfile(User user) {
    Profile profile = Profile.ofUser(user);
    profile = profileRepository.save(profile);
    logger.debug("Profile for user {} has been created", user.getUserId());
    return profile;
  }

  @Transactional
  @PreAuthorize("#userId == authentication.principal")
  public void updateProfile(String userId, UpdateProfileRequest request) {
    Profile profile =
        profileRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalStateException("User profile does not exist"));

    if (request.name() != null) {
      profile.changeName(request.name());
    }

    if (request.onboarded()) {
      profile.setOnboardingStatus(request.onboarded());
    }

    profileRepository.save(profile);
    logger.debug("Profile for user {} has been updated", userId);
  }
}
