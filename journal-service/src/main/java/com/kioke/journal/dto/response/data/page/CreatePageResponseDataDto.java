package com.kioke.journal.dto.response.data.page;

import com.kioke.journal.dto.response.data.ResponseDataDto;
import com.kioke.journal.model.Page;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePageResponseDataDto implements ResponseDataDto {
  private final String jid;
  private final String pid;
  private final LocalDate date;
  private final String template;
  private final OffsetDateTime createdAt;
  private final OffsetDateTime lastUpdated;

  public static CreatePageResponseDataDto from(Page page) {
    return CreatePageResponseDataDto.builder()
        .jid(page.getJid())
        .pid(page.getId())
        .date(page.getDate())
        .template(page.getTemplate())
        .createdAt(page.getCreatedAt().atOffset(ZoneOffset.UTC))
        .lastUpdated(page.getLastUpdated().atOffset(ZoneOffset.UTC))
        .build();
  }
}
