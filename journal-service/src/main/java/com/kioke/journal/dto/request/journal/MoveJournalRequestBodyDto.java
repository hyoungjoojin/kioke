package com.kioke.journal.dto.request.journal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveJournalRequestBodyDto {
  @NotNull private String shelfId;
}
