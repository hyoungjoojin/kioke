package io.kioke.feature.page.service;

import io.kioke.constant.Permission;
import io.kioke.feature.journal.service.JournalPermissionEvaluator;
import io.kioke.feature.page.repository.PageRepository;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class PagePermissionEvaluator implements PermissionEvaluator {

  private static Logger logger = LoggerFactory.getLogger(PagePermissionEvaluator.class);

  private final PageRepository pageRepository;

  private final JournalPermissionEvaluator journalPermissionEvaluator;

  public PagePermissionEvaluator(
      PageRepository pageRepository, JournalPermissionEvaluator journalPermissionEvaluator) {
    this.pageRepository = pageRepository;
    this.journalPermissionEvaluator = journalPermissionEvaluator;
  }

  @Override
  public boolean hasPermission(
      Authentication authentication, Object targetDomainObject, Object permission) {
    return true;
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

    String pageId = targetId.toString();
    String userId = authentication.getPrincipal().toString();

    return pageRepository
        .findJournalUserRole(pageId, userId)
        .map(
            permissionProjection ->
                journalPermissionEvaluator.hasPermission(permissionProjection, permission))
        .orElse(false);
  }
}
