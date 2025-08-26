package io.kioke.feature.journal.repository;

import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.domain.JournalRole;
import io.kioke.feature.journal.dto.JournalRoleDto;
import io.kioke.feature.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRoleRepository
    extends JpaRepository<JournalRole, JournalRole.JournalRoleId> {

  public Optional<JournalRole> findByUserAndJournal(User user, Journal journal);

  @Query("SELECT r.user.userId, r.role FROM JournalRole r WHERE r.journal = :journal")
  public List<JournalRoleDto> findByJournal(Journal journal);
}
