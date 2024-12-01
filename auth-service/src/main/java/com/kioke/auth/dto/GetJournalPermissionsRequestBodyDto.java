package com.kioke.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetJournalPermissionsRequestBodyDto {
  @NotNull private final String uid;
  @NotNull private final String jid;
}
