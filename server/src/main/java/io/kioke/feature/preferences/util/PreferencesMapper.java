package io.kioke.feature.preferences.util;

import io.kioke.feature.preferences.domain.Preferences;
import io.kioke.feature.preferences.dto.PreferencesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PreferencesMapper {

  public PreferencesDto toDto(Preferences preferences);
}
