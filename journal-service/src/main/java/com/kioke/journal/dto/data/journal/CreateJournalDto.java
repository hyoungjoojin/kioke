package com.kioke.journal.dto.data.journal;

import com.kioke.journal.dto.request.journal.CreateJournalRequestBodyDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateJournalDto {
  private final String title;
  private final String template;

  public static CreateJournalDto from(CreateJournalRequestBodyDto createJournalRequestBodyDto) {
    return CreateJournalDto.builder()
        .title(createJournalRequestBodyDto.getTitle())
        .template(createJournalRequestBodyDto.getTemplate())
        .build();
  }
}
