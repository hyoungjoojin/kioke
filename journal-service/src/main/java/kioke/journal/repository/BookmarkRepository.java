package kioke.journal.repository;

import java.util.Optional;
import kioke.journal.model.Bookmark;
import kioke.journal.model.Journal;
import kioke.journal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, String> {

  public Optional<Bookmark> findByUserAndJournal(User user, Journal journal);
}
