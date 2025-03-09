package com.kioke.journal.dto.response.journal;

import com.kioke.journal.model.Journal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetJournalResponseBodyDto {
  private String jid;
  private String title;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime lastModified;

  public static GetJournalResponseBodyDto from(Journal journal) {
    return GetJournalResponseBodyDto.builder()
        .jid(journal.getJid())
        .title(journal.getTitle())
        .description(journal.getDescription())
        .createdAt(journal.getCreatedAt())
        .lastModified(journal.getLastModified())
        .build();
  }
}
