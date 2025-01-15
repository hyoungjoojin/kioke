package com.kioke.journal.repository;

import com.kioke.journal.model.Journal;
import com.kioke.journal.model.JournalPermission;
import com.kioke.journal.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalPermissionRepository extends JpaRepository<JournalPermission, String> {
  public Optional<JournalPermission> findByUserAndJournal(User user, Journal journal);
}
