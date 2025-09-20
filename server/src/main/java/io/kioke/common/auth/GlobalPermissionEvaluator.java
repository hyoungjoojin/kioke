package io.kioke.common.auth;

import java.io.Serializable;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class GlobalPermissionEvaluator implements PermissionEvaluator {

  private static final Logger logger = LoggerFactory.getLogger(GlobalPermissionEvaluator.class);

  private final Map<String, PermissionEvaluator> permissionEvaluators;

  public GlobalPermissionEvaluator(Map<String, PermissionEvaluator> permissionEvaluators) {
    logger.debug(
        "Found {} permission evaluators: {}",
        permissionEvaluators.size(),
        permissionEvaluators.keySet());

    this.permissionEvaluators = permissionEvaluators;
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, Object targetDomainObject, Object permission) {
    logger.debug("Checking permission {} for object {}", permission, targetDomainObject);

    String evaluatorName = targetDomainObject.toString() + "PermissionEvaluator";
    PermissionEvaluator permissionEvaluator = permissionEvaluators.get(evaluatorName);

    if (permissionEvaluator == null) {
      logger.warn(
          "Could not find PermissionEvaluator for target domain object {}",
          targetDomainObject.toString());
      return false;
    }

    return permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, Serializable targetId, String targetType, Object permission) {
    logger.debug(
        "Checking permission {} for object {} with ID {}", permission, targetType, targetId);

    if (targetId == null) {
      logger.debug("Given target of type {} is null, permission denied", targetType);
      return false;
    }

    String evaluatorName = targetType + "PermissionEvaluator";
    PermissionEvaluator permissionEvaluator = permissionEvaluators.get(evaluatorName);

    if (permissionEvaluator == null) {
      logger.warn("Could not find PermissionEvaluator for target type {}", targetType);
      return false;
    }

    return permissionEvaluator.hasPermission(authentication, targetId, targetType, permission);
  }
}
