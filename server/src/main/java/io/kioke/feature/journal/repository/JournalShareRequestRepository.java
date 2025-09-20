package io.kioke.feature.journal.repository;

import io.kioke.feature.journal.domain.JournalShareRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalShareRequestRepository extends JpaRepository<JournalShareRequest, String> {}
