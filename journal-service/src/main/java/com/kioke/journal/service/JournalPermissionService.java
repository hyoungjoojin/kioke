package com.kioke.journal.service;

import com.kioke.journal.constant.Permission;
import com.kioke.journal.exception.permission.AccessDeniedException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.model.JournalPermission;
import com.kioke.journal.model.User;
import com.kioke.journal.repository.JournalPermissionRepository;
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
