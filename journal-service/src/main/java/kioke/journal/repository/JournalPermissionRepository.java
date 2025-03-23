package kioke.journal.repository;

import java.util.Optional;
import kioke.journal.model.Journal;
import kioke.journal.model.JournalPermission;
import kioke.journal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalPermissionRepository extends JpaRepository<JournalPermission, String> {
  public Optional<JournalPermission> findByUserAndJournal(User user, Journal journal);
}
