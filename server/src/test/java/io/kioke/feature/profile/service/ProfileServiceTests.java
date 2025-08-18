package io.kioke.feature.profile.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import autoparams.AutoParams;
import io.kioke.feature.profile.dto.request.UpdateProfileRequestDto;
import io.kioke.feature.profile.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTests {

  @InjectMocks private ProfileService profileService;

  @Mock private ProfileRepository profileRepository;

  @Test
  public void getMyProfile_userIsNull_throwIllegalArgumentException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          profileService.getMyProfile(null);
        });
  }

  @Test
  @AutoParams
  public void updateProfile_userIsNull_throwIllegalArgumentException(
      UpdateProfileRequestDto request) {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          profileService.updateProfile(null, request);
        });
  }
}
