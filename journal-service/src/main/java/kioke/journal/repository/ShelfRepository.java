package kioke.journal.repository;

import java.util.Optional;
import kioke.journal.model.Shelf;
import kioke.journal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfRepository extends JpaRepository<Shelf, String> {
  public Optional<Shelf> findByOwnerAndIsArchiveTrue(User user);
}
