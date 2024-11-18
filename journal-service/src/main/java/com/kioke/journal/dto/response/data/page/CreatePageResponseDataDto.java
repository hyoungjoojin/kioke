package com.kioke.journal.dto.response.data.page;

import com.kioke.journal.dto.response.data.ResponseDataDto;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePageResponseDataDto implements ResponseDataDto {
  private final String jid;
  private final LocalDate date;
  private final String template;
  private final OffsetDateTime createdAt;
  private final OffsetDateTime lastUpdated;
}
