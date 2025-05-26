package kioke.user.feature.preferences.repository;

import kioke.user.feature.preferences.domain.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferencesRepository
    extends JpaRepository<UserPreferences, String>, UserPreferencesRepositoryCustom {}
