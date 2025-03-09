package com.kioke.journal.dto.request.journal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateJournalRequestBodyDto {
  @NotNull private final String shelfId;
  @NotNull private final String title;
  @NotNull private final String description;
}
