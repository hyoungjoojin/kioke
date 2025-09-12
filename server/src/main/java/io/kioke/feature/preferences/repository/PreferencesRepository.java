package io.kioke.feature.preferences.repository;

import io.kioke.feature.preferences.domain.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, String> {}
