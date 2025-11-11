package io.kioke.feature.collection.domain;

import io.kioke.feature.journal.domain.Journal;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COLLECTION_ENTRY_TABLE")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CollectionEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @JoinColumn(name = "COLLECTION_ID")
  @ManyToOne(fetch = FetchType.LAZY)
  private Collection collection;

  @JoinColumn(name = "JOURNAL_ID")
  @ManyToOne(fetch = FetchType.LAZY)
  private Journal journal;
}
