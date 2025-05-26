package kioke.user.feature.preferences.repository;

import java.util.Optional;
import kioke.user.feature.preferences.dto.data.UserPreferencesDto;

public interface UserPreferencesRepositoryCustom {

  public Optional<UserPreferencesDto> getUserPreferencesByUserId(String userId);
}
