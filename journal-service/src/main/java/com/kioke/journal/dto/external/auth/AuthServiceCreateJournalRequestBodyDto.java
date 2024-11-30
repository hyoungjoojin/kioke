package com.kioke.journal.dto.external.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthServiceCreateJournalRequestBodyDto {
  private String jid;
  private String uid;
}
