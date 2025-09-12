package io.kioke.feature.preferences.service;

import io.kioke.feature.preferences.domain.Preferences;
import io.kioke.feature.preferences.dto.PreferencesDto;
import io.kioke.feature.preferences.dto.request.UpdatePreferencesRequest;
import io.kioke.feature.preferences.repository.PreferencesRepository;
import io.kioke.feature.preferences.util.PreferencesMapper;
import io.kioke.feature.user.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PreferencesService {

  private static final Logger logger = LoggerFactory.getLogger(PreferencesService.class);

  private final PreferencesRepository preferencesRepository;
  private final PreferencesMapper preferencesMapper;

  public PreferencesService(
      PreferencesRepository preferencesRepository, PreferencesMapper preferencesMapper) {
    this.preferencesRepository = preferencesRepository;
    this.preferencesMapper = preferencesMapper;
  }

  @Transactional
  public void createPreferences(UserDto user) {
    Preferences preferences = Preferences.of(user.userId());
    preferencesRepository.save(preferences);
    logger.debug("Preferences has been created for user {}", user.userId());
  }

  @Transactional(readOnly = true)
  public PreferencesDto getPreferences(UserDto user) {
    Preferences preferences = preferencesRepository.findById(user.userId()).orElseThrow();
    return preferencesMapper.toDto(preferences);
  }

  @Transactional(readOnly = true)
  public void updatePreferences(UserDto user, UpdatePreferencesRequest request) {
    Preferences preferences = preferencesRepository.findById(user.userId()).orElseThrow();

    if (request.theme() != null) {
      preferences.setTheme(request.theme());
    }

    preferencesRepository.save(preferences);
  }
}
