package kioke.user.feature.preferences.domain;

public enum Theme {
  SYSTEM("system"),
  LIGHT("light"),
  DARK("dark");

  private String themeId;

  private Theme(String themeId) {
    this.themeId = themeId;
  }

  @Override
  public String toString() {
    return themeId;
  }
}
