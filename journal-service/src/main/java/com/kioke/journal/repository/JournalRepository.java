package com.kioke.journal.repository;

import com.kioke.journal.model.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal, String> {}
