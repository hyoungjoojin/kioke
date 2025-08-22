package io.kioke.feature.journal.repository;

import io.kioke.feature.journal.domain.Journal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<Journal, String> {

  @Query("SELECT j FROM Journal j LEFT JOIN FETCH j.pages WHERE j.journalId = :journalId")
  public Optional<Journal> findById(String journalId);
}
