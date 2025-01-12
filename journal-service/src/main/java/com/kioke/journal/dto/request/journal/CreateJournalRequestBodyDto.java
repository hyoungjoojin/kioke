package com.kioke.journal.dto.request.journal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateJournalRequestBodyDto {
  private static final String DEFAULT_TITLE = "";

  private final String title;

  public CreateJournalRequestBodyDto() {
    this.title = DEFAULT_TITLE;
  }
}
