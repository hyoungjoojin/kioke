package kioke.journal.repository;

import kioke.journal.model.UserJournalMetadata;
import kioke.journal.repository.custom.UserJournalMetadataRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJournalMetadataRepository
    extends JpaRepository<UserJournalMetadata, String>, UserJournalMetadataRepositoryCustom {}
