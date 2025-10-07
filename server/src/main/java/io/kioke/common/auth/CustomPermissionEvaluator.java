package io.kioke.common.auth;

import org.springframework.security.core.Authentication;

public interface CustomPermissionEvaluator {

  public PermissionEvaluatorType type();

  public boolean hasPermission(Authentication authentication, Permission permission);

  public boolean hasPermission(
      Authentication authentication, String targetId, Permission permission);
}
