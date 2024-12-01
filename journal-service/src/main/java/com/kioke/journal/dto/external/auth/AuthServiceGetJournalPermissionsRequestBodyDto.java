package com.kioke.journal.dto.external.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthServiceGetJournalPermissionsRequestBodyDto {
  private final String uid;
  private final String jid;
}
