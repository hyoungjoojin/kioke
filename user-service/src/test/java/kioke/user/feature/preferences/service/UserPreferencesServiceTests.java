package kioke.user.feature.preferences.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Optional;
import kioke.user.feature.preferences.domain.UserPreferences;
import kioke.user.feature.preferences.dto.data.UserPreferencesDto;
import kioke.user.feature.preferences.repository.UserPreferencesRepository;
import kioke.user.feature.user.domain.User;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserPreferencesServiceTests {

  @InjectMocks private UserPreferencesService userPreferencesService;

  @Mock private UserPreferencesRepository userPreferencesRepository;

  private Faker faker = new Faker();

  private User user =
      new User(faker.internet().uuid(), faker.internet().emailAddress(), faker.name().fullName());
  private UserPreferences userPreferences = new UserPreferences();

  @Test
  public void
      test_getUserPreferencesByUserId_givenUserId_whenUserIdIsNull_thenThrowNullPointerException()
          throws Exception {
    assertThrows(
        NullPointerException.class,
        () -> {
          userPreferencesService.getUserPreferencesByUserId(null);
        });

    verifyNoInteractions(userPreferencesRepository);
  }

  @Test
  public void
      test_getUserPreferencesByUserId_givenUserId_whenRepositoryReturnsPreferences_thenReturnPreferences()
          throws Exception {
    given(userPreferencesRepository.getUserPreferencesByUserId(user.getUserId()))
        .willReturn(Optional.of(UserPreferencesDto.from(userPreferences)));

    assertEquals(
        UserPreferencesDto.from(userPreferences),
        userPreferencesService.getUserPreferencesByUserId(user.getUserId()));
  }

  @Test
  public void
      test_getUserPreferencesByUserId_givenUserId_whenRepositoryReturnsEmptyOptional_thenReturnDefaultPreferences()
          throws Exception {
    given(userPreferencesRepository.getUserPreferencesByUserId(user.getUserId()))
        .willReturn(Optional.empty());

    UserPreferences expected = new UserPreferences();
    assertEquals(
        UserPreferencesDto.from(expected),
        userPreferencesService.getUserPreferencesByUserId(user.getUserId()));
  }
}
