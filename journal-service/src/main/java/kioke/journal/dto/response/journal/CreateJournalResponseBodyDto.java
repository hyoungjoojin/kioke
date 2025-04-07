package kioke.journal.dto.response.journal;

import kioke.journal.model.Journal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateJournalResponseBodyDto {
  private String jid;
  private String title;

  public static CreateJournalResponseBodyDto from(Journal journal) {
    return CreateJournalResponseBodyDto.builder()
        .jid(journal.getJid())
        .title(journal.getTitle())
        .build();
  }
}
