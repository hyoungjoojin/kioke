package com.kioke.journal.repository;

import com.kioke.journal.model.Journal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<Journal, String> {}
