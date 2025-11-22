package io.kioke.feature.journal.repository;

import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.dto.projection.JournalPermissionProjection;
import io.kioke.feature.page.domain.Page;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<Journal, String> {

  @Query(
      """
      SELECT
        j
      FROM
        Journal j
      WHERE
        LOWER(j.title) LIKE LOWER(CONCAT('%', :query, '%'))
        AND (:after IS NULL OR j.id > :after)
      ORDER BY j.id ASC
      """)
  public org.springframework.data.domain.Page<Journal> findAllByQuery(
      String query, Pageable pageable, String after);

  @EntityGraph(
      attributePaths = {"users", "cover"},
      type = EntityGraphType.FETCH)
  @Query("SELECT j FROM Journal j WHERE j.id = :journalId")
  public Optional<Journal> findById(String journalId);

  @Query("SELECT p FROM Page p WHERE p.journal.id = :journalId")
  public List<Page> findPagesById(String journalId);

  @Query(
      """
        SELECT u.role, j.isPublic
          FROM Journal j
            LEFT JOIN JournalUser u ON j.id = u.journal.id
        WHERE
          j.id = :journalId AND u.user.userId = :userId
      """)
  public Optional<JournalPermissionProjection> findJournalUserRole(String journalId, String userId);
}
