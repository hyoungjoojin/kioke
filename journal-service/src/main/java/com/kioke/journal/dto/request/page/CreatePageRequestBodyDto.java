package com.kioke.journal.dto.request.page;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePageRequestBodyDto {
  private final LocalDate date;
  private final String template;
}
