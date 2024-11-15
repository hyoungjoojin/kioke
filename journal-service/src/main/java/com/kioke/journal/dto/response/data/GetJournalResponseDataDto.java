package com.kioke.journal.dto.response.data;

import com.kioke.journal.model.Journal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetJournalResponseDataDto implements ResponseDataDto {
  private final String jid;
  private final String title;
  private final String template;
  private final OffsetDateTime createdAt;
  private final OffsetDateTime lastUpdated;

  public static GetJournalResponseDataDto from(Journal journal) {
    return GetJournalResponseDataDto.builder()
        .jid(journal.getId())
        .title(journal.getTitle())
        .template(journal.getTemplate())
        .createdAt(journal.getCreatedAt().atOffset(ZoneOffset.UTC))
        .lastUpdated(journal.getLastUpdated().atOffset(ZoneOffset.UTC))
        .build();
  }
}
