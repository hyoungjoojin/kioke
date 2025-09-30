package io.kioke.feature.journal.service;

import io.kioke.common.auth.CustomPermissionEvaluator;
import io.kioke.common.auth.Permission;
import io.kioke.common.auth.PermissionEvaluatorType;
import io.kioke.common.auth.PermissionObject;
import io.kioke.feature.journal.domain.JournalRole;
import io.kioke.feature.journal.dto.projection.JournalPermissionProjection;
import io.kioke.feature.journal.repository.JournalRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JournalPermissionEvaluator implements CustomPermissionEvaluator {

  private JournalRepository journalRepository;

  public JournalPermissionEvaluator(JournalRepository journalRepository) {
    this.journalRepository = journalRepository;
  }

  @Override
  public PermissionEvaluatorType type() {
    return PermissionEvaluatorType.JOURNAL;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Permission permission) {
    if (permission.equals(Permission.CREATE)) {
      return true;
    }

    return false;
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, String journalId, Permission permission) {
    String userId = authentication.getPrincipal().toString();

    return journalRepository
        .findJournalUserRole(journalId, userId)
        .map(permissionProjection -> hasPermission(permissionProjection, permission))
        .orElse(false);
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, Permission permission, PermissionObject permissionObject) {
    throw new UnsupportedOperationException();
  }

  public boolean hasPermission(
      JournalPermissionProjection permissionProjection, Permission permission) {
    if (permissionProjection.isJournalPublic() && permission.equals(Permission.READ)) {
      return true;
    }

    JournalRole role = permissionProjection.role();
    switch (permission) {
      case Permission.READ:
        if (role.canRead()) {
          return true;
        }

        break;

      case Permission.UPDATE:
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
