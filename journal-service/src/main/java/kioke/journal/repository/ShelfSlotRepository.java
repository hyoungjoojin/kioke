package kioke.journal.repository;

import java.util.Optional;
import kioke.journal.model.Journal;
import kioke.journal.model.ShelfSlot;
import kioke.journal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfSlotRepository extends JpaRepository<ShelfSlot, String> {
  public Optional<ShelfSlot> findByUserAndJournal(User user, Journal journal);
}
