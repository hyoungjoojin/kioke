package io.kioke.feature.journal.repository;

import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.dto.projection.JournalPermissionProjection;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JournalRepository extends JpaRepository<Journal, String> {

  @Query(
      """
        SELECT u.role, j.isPublic
          FROM Journal j
            LEFT JOIN JournalUser u ON j.journalId = u.journal.journalId
        WHERE
          j.journalId = :journalId AND u.user.userId = :userId
      """)
  public Optional<JournalPermissionProjection> findJournalUserRole(String journalId, String userId);

  @EntityGraph(
      attributePaths = {"users"},
      type = EntityGraphType.FETCH)
  @Query("SELECT j FROM Journal j WHERE j.journalId = :journalId")
  public Optional<Journal> findWithUsersById(String journalId);

  @EntityGraph(
      attributePaths = {"pages"},
      type = EntityGraphType.FETCH)
  @Query("SELECT j FROM Journal j WHERE j.journalId = :journalId")
  public Optional<Journal> findWithPagesById(String journalId);

  @Modifying
  @Transactional
  @Query("DELETE FROM Journal j WHERE j.isDeleted = TRUE")
  public void deleteJournalsOlderThen30Days();
}
