package com.kioke.auth.repository;

import com.kioke.auth.model.AclEntry;
import com.kioke.auth.model.Journal;
import com.kioke.auth.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AclRepository extends JpaRepository<AclEntry, String> {
  public Optional<AclEntry> findByUserAndJournal(User user, Journal journal);
}
