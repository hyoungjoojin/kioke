package com.kioke.user.constant;

import java.util.Optional;

public enum Locale {
  ENGLISH("en"),
  KOREAN("kr");

  private String code;

  private Locale(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public static Optional<Locale> fromCode(String code) {
    for (Locale language : Locale.values()) {
      if (code.matches(language.code)) return Optional.of(language);
    }

    return Optional.empty();
  }
}
