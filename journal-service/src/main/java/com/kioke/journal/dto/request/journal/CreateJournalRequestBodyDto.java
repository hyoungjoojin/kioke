package com.kioke.journal.dto.request.journal;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateJournalRequestBodyDto {
  @NotNull private final String title;
  @NotNull private final String template;
}
