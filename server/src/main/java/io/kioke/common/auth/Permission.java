package io.kioke.common.auth;

public enum Permission {
  CREATE("create"),
  READ("read"),
  UPDATE("update"),
  DELETE("delete"),
  SHARE("share");

  private String permission;

  private Permission(String permission) {
    this.permission = permission;
  }

  public static Permission of(String permission) {
    for (Permission p : Permission.values()) {
      if (p.permission.equals(permission)) {
        return p;
      }
    }

    throw new IllegalArgumentException();
  }
}
