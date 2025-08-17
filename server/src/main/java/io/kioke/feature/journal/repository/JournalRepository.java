package io.kioke.feature.journal.repository;

import io.kioke.feature.journal.domain.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository
    extends JpaRepository<Journal, String>, CustomizedJournalRepository {}
