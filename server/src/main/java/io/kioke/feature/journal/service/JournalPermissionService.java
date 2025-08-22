package io.kioke.feature.journal.service;

import io.kioke.constant.Permission;
import io.kioke.exception.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.domain.JournalPermission;
import io.kioke.feature.journal.domain.JournalPermissionId;
import io.kioke.feature.journal.repository.JournalPermissionRepository;
import io.kioke.feature.user.domain.User;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalPermissionService {

  private final JournalPermissionRepository journalPermissionRepository;

  public JournalPermissionService(JournalPermissionRepository journalPermissionRepository) {
    this.journalPermissionRepository = journalPermissionRepository;
  }

  @Transactional(readOnly = true)
  public void checkPermissions(User user, Journal journal, Set<Permission> permissions)
      throws AccessDeniedException, JournalNotFoundException {
    if (journal.getAuthor().getUserId().equals(user.getUserId())) {
      return;
    }

    JournalPermission entry =
        journalPermissionRepository
            .findById(JournalPermissionId.from(user.getUserId(), journal.getJournalId()))
            .orElse(JournalPermission.ofEmpty());

    for (Permission p : permissions) {
      switch (p) {
        case Permission.READ:
          if (!journal.getIsPublic() && !entry.canRead()) {
            throw new JournalNotFoundException();
          }

          break;

        case Permission.EDIT:
          if (!entry.canEdit()) {
            throw new AccessDeniedException();
          }
          break;

        case Permission.DELETE:
          if (!entry.canDelete()) {
            throw new AccessDeniedException();
          }
          break;
      }
    }
  }
}
