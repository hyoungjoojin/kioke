package com.kioke.user.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kioke.user.constant.Locale;
import com.kioke.user.constant.Theme;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.Data;

@Data
public class Preferences {
  private Locale locale = Locale.ENGLISH;
  private Theme theme = Theme.DARK_MODE;

  public Preferences() {
    this.locale = Locale.ENGLISH;
    this.theme = Theme.DARK_MODE;
  }

  @Converter
  public static class PreferencesConverter implements AttributeConverter<Preferences, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Preferences preferences) {
      try {
        return objectMapper.writeValueAsString(preferences);
      } catch (JsonProcessingException e) {
        return null;
      }
    }

    @Override
    public Preferences convertToEntityAttribute(String value) {
      try {
        return objectMapper.readValue(value, Preferences.class);
      } catch (JsonProcessingException e) {
        return null;
      }
    }
  }
}
