package kioke.journal.repository.custom;

import java.util.List;
import java.util.Optional;
import kioke.journal.dto.data.journal.JournalPreviewDto;
import kioke.journal.model.UserJournalMetadata;

public interface UserJournalMetadataRepositoryCustom {

  public Optional<UserJournalMetadata> findByUserIdAndJournalId(String userId, String journalId);

  public List<JournalPreviewDto> findAllJournalIdsByUser(
      String userId, boolean findOnlyBookmarkedJournals);
}
