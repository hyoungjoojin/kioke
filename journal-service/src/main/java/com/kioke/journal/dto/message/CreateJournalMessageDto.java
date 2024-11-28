package com.kioke.journal.dto.message;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateJournalMessageDto {
  private String uid;
  private String jid;
}
