package io.kioke.common.auth;

import java.util.Arrays;

public enum Permission {
  CREATE("create"),
  READ("read"),
  UPDATE("update"),
  DELETE("delete"),
  SHARE("share");

  private String key;

  private Permission(String key) {
    this.key = key;
  }

  private String getKey() {
    return key;
  }

  public static Permission of(String key) {
    for (Permission p : Permission.values()) {
      if (p.key.equals(key)) {
        return p;
      }
    }

    throw new IllegalArgumentException(
        String.format(
            "Unknown permission key %s. Permission key has to one of: %s",
            key,
            Arrays.asList(Permission.values()).stream()
                .map(Permission::getKey)
                .toList()
                .toString()));
  }
}
