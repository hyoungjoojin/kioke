package io.kioke.common.auth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class GlobalPermissionEvaluator implements PermissionEvaluator {

  private final Map<PermissionEvaluatorType, CustomPermissionEvaluator> permissionEvaluators;

  public GlobalPermissionEvaluator(List<CustomPermissionEvaluator> permissionEvaluators) {
    this.permissionEvaluators = new HashMap<>();
    permissionEvaluators.forEach(
        permissionEvaluator -> {
          this.permissionEvaluators.put(permissionEvaluator.type(), permissionEvaluator);
        });
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, Object targetDomainObject, Object permissionString) {
    Permission permission = Permission.of(permissionString.toString());

    if (targetDomainObject instanceof String) {
      return hasPermission(authentication, (String) targetDomainObject, permission);
    } else {
      throw new IllegalArgumentException("Call to hasPermission must of String type");
    }
  }

  @Override
  public boolean hasPermission(
      Authentication authentication,
      Serializable targetId,
      String targetType,
      Object permissionString) {
    if (targetId == null) {
      return false;
    }

    Permission permission = Permission.of(permissionString.toString());
    PermissionEvaluatorType type = PermissionEvaluatorType.of(targetType);
    CustomPermissionEvaluator permissionEvaluator = permissionEvaluators.get(type);

    return permissionEvaluator.hasPermission(authentication, targetId.toString(), permission);
  }

  private boolean hasPermission(
      Authentication authentication, String targetDomainObject, Permission permission) {
    PermissionEvaluatorType type = PermissionEvaluatorType.of(targetDomainObject.toString());
    CustomPermissionEvaluator permissionEvaluator = getPermissionEvaluator(type);

    return permissionEvaluator.hasPermission(authentication, permission);
  }

  private CustomPermissionEvaluator getPermissionEvaluator(PermissionEvaluatorType type) {
    CustomPermissionEvaluator permissionEvaluator = permissionEvaluators.get(type);

    if (permissionEvaluator == null) {
      throw new IllegalArgumentException();
    }

    return permissionEvaluator;
  }
}
