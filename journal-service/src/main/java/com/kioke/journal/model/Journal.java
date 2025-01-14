package com.kioke.journal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "JOURNAL_TABLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Journal {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String jid;

  @OneToMany(mappedBy = "journal")
  private List<JournalPermission> users;

  @NotNull private String title;
}
