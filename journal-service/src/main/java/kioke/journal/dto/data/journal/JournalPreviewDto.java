package kioke.journal.dto.data.journal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import kioke.journal.model.Journal;
import lombok.Builder;

@Builder
public record JournalPreviewDto(
    String journalId,
    String title,
    @JsonProperty("bookmarked") boolean bookmarked,
    LocalDateTime createdAt) {

  public static JournalPreviewDto from(Journal journal, boolean bookmarked) {
    return JournalPreviewDto.builder()
        .journalId(journal.getJournalId())
        .title(journal.getTitle())
        .createdAt(journal.getCreatedAt())
        .bookmarked(bookmarked)
        .build();
  }
}
