package kioke.user.constant;

public enum Theme {
  LIGHT_MODE,
  DARK_MODE;

  @Override
  public String toString() {
    switch (this) {
      case LIGHT_MODE:
        return "light_mode";

      case DARK_MODE:
        return "dark_mode";

      default:
        return "";
    }
  }
}
