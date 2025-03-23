package kioke.journal.service;

import kioke.journal.constant.Permission;
import kioke.journal.exception.permission.AccessDeniedException;
import kioke.journal.model.Journal;
import kioke.journal.model.JournalPermission;
import kioke.journal.model.User;
import kioke.journal.repository.JournalPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class JournalPermissionService {
  @Autowired @Lazy JournalPermissionRepository journalPermissionRepository;

  public void checkReadPermissions(User user, Journal journal) throws AccessDeniedException {
    JournalPermission permission =
        journalPermissionRepository
            .findByUserAndJournal(user, journal)
            .orElseThrow(() -> new AccessDeniedException());

    if (permission.getReadPermission().equals(Permission.PERMITTED)) return;

    throw new AccessDeniedException();
  }

  public void checkEditPermissions(User user, Journal journal) throws AccessDeniedException {
    JournalPermission permission =
        journalPermissionRepository
            .findByUserAndJournal(user, journal)
            .orElseThrow(() -> new AccessDeniedException());

    if (permission.getEditPermission().equals(Permission.PERMITTED)) return;

    throw new AccessDeniedException();
  }

  public void checkDeletePermissions(User user, Journal journal) throws AccessDeniedException {
    JournalPermission permission =
        journalPermissionRepository
            .findByUserAndJournal(user, journal)
            .orElseThrow(() -> new AccessDeniedException());

    if (permission.getDeletePermission().equals(Permission.PERMITTED)) return;

    throw new AccessDeniedException();
  }

  public void grantAuthorPermissionsToUser(User user, Journal journal) {
    JournalPermission permission =
        JournalPermission.builder()
            .user(user)
            .journal(journal)
            .readPermission(Permission.PERMITTED)
            .editPermission(Permission.PERMITTED)
            .deletePermission(Permission.PERMITTED)
            .build();

    journalPermissionRepository.save(permission);
  }
}
