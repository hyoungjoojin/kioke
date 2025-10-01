package io.kioke.common.auth;

public enum PermissionEvaluatorType {
  JOURNAL("journal"),
  PAGE("page"),
  MEDIA("media");

  private String type;

  private PermissionEvaluatorType(String type) {
    this.type = type;
  }

  public static PermissionEvaluatorType of(String type) {
    for (PermissionEvaluatorType permissionEvaluatorType : PermissionEvaluatorType.values()) {
      if (permissionEvaluatorType.type.equals(type)) {
        return permissionEvaluatorType;
      }
    }

    throw new IllegalArgumentException(
        "Could not resolve  " + type + " as PermissionEvaluatorType");
  }
}
