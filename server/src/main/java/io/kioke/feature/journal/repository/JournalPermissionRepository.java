package io.kioke.feature.journal.repository;

import io.kioke.feature.journal.domain.JournalPermission;
import io.kioke.feature.journal.domain.JournalPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalPermissionRepository
    extends JpaRepository<JournalPermission, JournalPermissionId> {}
