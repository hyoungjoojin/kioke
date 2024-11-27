package com.kioke.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "JOURNAL_TABLE")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Journal {
  @Id
  @Column(unique = true, nullable = false)
  private String jid;

  @ManyToOne private User owner;
}
