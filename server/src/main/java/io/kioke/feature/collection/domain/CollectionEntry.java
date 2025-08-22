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

@Entity
@Table(name = "COLLECTION_ENTRY_TABLE")
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

  protected CollectionEntry() {}

  private CollectionEntry(Collection collection, Journal journal) {
    this.collection = collection;
    this.journal = journal;
  }

  public static CollectionEntry from(Collection collection, Journal journal) {
    return new CollectionEntry(collection, journal);
  }

  public Journal getJournal() {
    return journal;
  }
}
