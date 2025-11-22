package io.kioke.feature.journal.repository;

import io.kioke.feature.journal.domain.JournalUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JournalUserRepository extends JpaRepository<JournalUser, String> {

  @Query("SELECT u FROM JournalUser u WHERE u.journal.id = :journalId AND u.user.userId = :userId")
  public Optional<JournalUser> findJournalUser(String journalId, String userId);
}
