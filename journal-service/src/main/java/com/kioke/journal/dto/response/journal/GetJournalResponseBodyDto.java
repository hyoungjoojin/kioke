package com.kioke.journal.dto.response.journal;

import com.kioke.journal.model.Journal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetJournalResponseBodyDto {
  private String jid;
  private String title;

  public static GetJournalResponseBodyDto from(Journal journal) {
    return GetJournalResponseBodyDto.builder()
        .jid(journal.getJid())
        .title(journal.getTitle())
        .build();
  }
}
