package io.kioke.feature.media.service;

import io.kioke.common.auth.CustomPermissionEvaluator;
import io.kioke.common.auth.Permission;
import io.kioke.common.auth.PermissionEvaluatorType;
import io.kioke.common.auth.PermissionObject;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class MediaPermissionEvaluator implements CustomPermissionEvaluator {

  @Override
  public PermissionEvaluatorType type() {
    return PermissionEvaluatorType.MEDIA;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Permission permission) {
    // TODO:
    return true;
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, String targetId, Permission permission) {
    // TODO:
    return true;
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, Permission permission, PermissionObject permissionObject) {
    // TODO:
    return true;
  }
}
