package com.kioke.auth.repository;

import com.kioke.auth.model.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal, String> {}
