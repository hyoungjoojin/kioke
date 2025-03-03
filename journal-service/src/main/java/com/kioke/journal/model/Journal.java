package com.kioke.journal.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  @OneToMany(mappedBy = "journal", orphanRemoval = true)
  private List<JournalPermission> users;

  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  @JoinColumn(name = "SHELF_ID")
  private Shelf shelf;

  @NotNull private String title;

  @NotNull private boolean isDeleted;
}
