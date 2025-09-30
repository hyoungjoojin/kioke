package io.kioke.feature.page.service;

import io.kioke.common.auth.CustomPermissionEvaluator;
import io.kioke.common.auth.Permission;
import io.kioke.common.auth.PermissionEvaluatorType;
import io.kioke.common.auth.PermissionObject;
import io.kioke.feature.journal.service.JournalPermissionEvaluator;
import io.kioke.feature.page.repository.PageRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class PagePermissionEvaluator implements CustomPermissionEvaluator {

  private final PageRepository pageRepository;
  private final JournalPermissionEvaluator journalPermissionEvaluator;

  public PagePermissionEvaluator(
      PageRepository pageRepository, JournalPermissionEvaluator journalPermissionEvaluator) {
    this.pageRepository = pageRepository;
    this.journalPermissionEvaluator = journalPermissionEvaluator;
  }

  @Override
  public PermissionEvaluatorType type() {
    return PermissionEvaluatorType.PAGE;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Permission permission) {
    return true;
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, String pageId, Permission permission) {
    String userId = authentication.getPrincipal().toString();

    return pageRepository
        .findJournalUserRole(pageId, userId)
        .map(
            permissionProjection ->
                journalPermissionEvaluator.hasPermission(permissionProjection, permission))
        .orElse(false);
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, Permission permission, PermissionObject permissionObject) {
    throw new UnsupportedOperationException();
  }
}
