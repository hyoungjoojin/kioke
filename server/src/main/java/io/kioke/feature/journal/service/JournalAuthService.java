package io.kioke.feature.journal.service;

import io.kioke.constant.Permission;
import io.kioke.exception.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.domain.JournalPermission;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.dto.JournalPermissionDto;
import io.kioke.feature.journal.repository.JournalPermissionRepository;
import io.kioke.feature.journal.repository.JournalRepository;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalAuthService {

  private final JournalRepository journalRepository;
  private final JournalPermissionRepository journalPermissionRepository;

  public JournalAuthService(
      JournalRepository journalRepository,
      JournalPermissionRepository journalPermissionRepository) {
    this.journalRepository = journalRepository;
    this.journalPermissionRepository = journalPermissionRepository;
  }

  @Transactional(readOnly = true)
  public void checkPermissions(UserDto user, JournalDto journal, List<Permission> permissions)
      throws JournalNotFoundException, AccessDeniedException {
    for (Permission permission : permissions) {
      checkPermissions(user, journal, permission);
    }
  }

  @Transactional(readOnly = true)
  public void checkPermissions(UserDto user, JournalDto journal, Permission permission)
      throws JournalNotFoundException, AccessDeniedException {
    JournalPermissionDto permissions =
        journalRepository.findPermissions(user, journal).orElse(new JournalPermissionDto());

    switch (permission) {
      case Permission.READ:
        if (!permissions.isJournalPublic() && !permissions.canRead()) {
          throw new JournalNotFoundException();
        }

        break;

      case Permission.EDIT:
        if (!permissions.canEdit()) {
          throw new AccessDeniedException();
        }
        break;

      case Permission.DELETE:
        if (!permissions.canDelete()) {
          throw new AccessDeniedException();
        }
        break;
    }
  }

  @Transactional
  public void setPermissions(UserDto user, JournalDto journal, List<Permission> permissions) {
    JournalPermission permission = new JournalPermission();
    for (Permission p : permissions) {
      switch (p) {
        case Permission.READ:
          permission.setCanRead(true);
          break;

        case Permission.EDIT:
          permission.setCanEdit(true);
          break;

        case Permission.DELETE:
          permission.setCanDelete(true);
          break;
      }
    }

    User userReference = new User();
    userReference.setUserId(user.userId());

    Journal journalReference = new Journal();
    journalReference.setJournalId(journal.journalId());

    permission.setUser(userReference);
    permission.setJournal(journalReference);
    journalPermissionRepository.save(permission);
  }
}
