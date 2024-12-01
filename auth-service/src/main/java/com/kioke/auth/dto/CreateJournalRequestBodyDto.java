package com.kioke.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateJournalRequestBodyDto {
  @NotNull private String uid;
  @NotNull private String jid;
}
