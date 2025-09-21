package io.kioke.feature.page.service;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class BlockPermissionEvaluator implements PermissionEvaluator {

  private static Logger logger = LoggerFactory.getLogger(BlockPermissionEvaluator.class);

  @Override
  public boolean hasPermission(
      Authentication authentication, Object targetDomainObject, Object permission) {
    return true;
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, Serializable targetId, String targetType, Object permission) {
    if (targetId == null) {
      logger.debug("Target ID is empty, permission denied");
      return false;
    }

    return true;
  }
}
