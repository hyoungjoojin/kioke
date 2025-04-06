package kioke.journal.repository;

import java.util.Optional;
import kioke.journal.model.Journal;
import kioke.journal.model.JournalRole;
import kioke.journal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRoleRepository extends JpaRepository<JournalRole, String> {
  public Optional<JournalRole> findByUserAndJournal(User user, Journal journal);
}
