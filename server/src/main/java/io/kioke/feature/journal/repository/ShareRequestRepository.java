package io.kioke.feature.journal.repository;

import io.kioke.feature.journal.domain.ShareRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRequestRepository extends JpaRepository<ShareRequest, String> {}
