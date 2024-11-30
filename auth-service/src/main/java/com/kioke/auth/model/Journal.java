package com.kioke.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "JOURNAL_TABLE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Journal {
  @Id
  @Column(name = "jid")
  private String jid;
}
