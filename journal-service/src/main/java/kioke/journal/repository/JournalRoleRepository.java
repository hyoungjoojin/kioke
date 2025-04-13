package kioke.journal.repository;

import java.util.List;
import java.util.Optional;
import kioke.journal.model.JournalRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JournalRoleRepository extends JpaRepository<JournalRole, String> {

  @Query("SELECT e FROM JournalRole e WHERE e.user.id = :userId AND e.journal.id = :journalId")
  public Optional<JournalRole> findByUserIdAndJournalId(
      @Param("userId") String userId, @Param("journalId") String journalId);

  @Query("SELECT e.journal.id FROM JournalRole e WHERE e.user.id = :userId")
  public List<String> findAllJournalIdsByUser(@Param("userId") String userId);
}
