package io.kioke.feature.journal.service;

import io.kioke.constant.Permission;
import io.kioke.feature.journal.domain.JournalRole;
import io.kioke.feature.journal.dto.projection.JournalPermissionProjection;
import io.kioke.feature.journal.repository.JournalRepository;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JournalPermissionEvaluator implements PermissionEvaluator {

  private static final Logger logger = LoggerFactory.getLogger(JournalPermissionEvaluator.class);

  private JournalRepository journalRepository;

  public JournalPermissionEvaluator(JournalRepository journalRepository) {
    this.journalRepository = journalRepository;
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, Object targetDomainObject, Object permissionObject) {
    Permission permission = Permission.valueOf(permissionObject.toString());

    if (permission.equals(Permission.CREATE)) {
      return true;
    }

    return false;
  }

  @Override
  public boolean hasPermission(
      Authentication authentication,
      Serializable targetId,
      String targetType,
      Object permissionObject) {
    if (targetId == null) {
      logger.debug("Target ID is empty, permission denied");
      return false;
    }

    Permission permission = Permission.valueOf(permissionObject.toString());

    String journalId = targetId.toString();
    String userId = authentication.getPrincipal().toString();

    return journalRepository
        .findJournalUserRole(journalId, userId)
        .map(journalPermission -> hasPermission(journalPermission, permission))
        .orElse(false);
  }

  private boolean hasPermission(
      JournalPermissionProjection journalPermission, Permission permission) {
    if (journalPermission.isPublic() && permission.equals(Permission.READ)) {
      return true;
    }

    JournalRole role = journalPermission.role();
    switch (permission) {
      case Permission.READ:
        if (role.canRead()) {
          return true;
        }

        break;

      case Permission.EDIT:
        if (role.canEdit()) {
          return true;
        }
        break;

      case Permission.DELETE:
        if (role.canDelete()) {
          return true;
        }
        break;

      default:
        break;
    }

    return false;
  }
}
