package com.kioke.journal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
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

  @OneToMany(mappedBy = "journal", orphanRemoval = true)
  private List<ShelfSlot> shelves;

  @NotNull private String title;

  @NotNull private String description;

  @NotNull private boolean isDeleted;

  @CreatedDate @NotNull private LocalDateTime createdAt;

  @LastModifiedDate @NotNull private LocalDateTime lastModified;
}
