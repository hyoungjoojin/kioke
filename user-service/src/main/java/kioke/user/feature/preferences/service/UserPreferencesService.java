package kioke.user.feature.preferences.service;

import java.util.Objects;
import java.util.Optional;
import kioke.user.feature.preferences.domain.UserPreferences;
import kioke.user.feature.preferences.dto.data.UserPreferencesDto;
import kioke.user.feature.preferences.repository.UserPreferencesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPreferencesService {

  private UserPreferencesRepository userPreferencesRepository;

  public UserPreferencesService(UserPreferencesRepository userPreferencesRepository) {
    this.userPreferencesRepository = userPreferencesRepository;
  }

  @Transactional(readOnly = true)
  public UserPreferencesDto getUserPreferencesByUserId(String userId) {
    Objects.requireNonNull(userId, "Parameter userId must not be null.");

    return userPreferencesRepository
        .getUserPreferencesByUserId(userId)
        .or(() -> Optional.of(UserPreferencesDto.from(new UserPreferences())))
        .get();
  }
}
