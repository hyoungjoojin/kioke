package io.kioke.feature.collection.domain;

import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

@Entity
@Table(name = "COLLECTION_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class Collection {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "COLLECTION_ID")
  private String collectionId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  @Column(name = "NAME", nullable = false)
  private String name;

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "collection",
      cascade = {CascadeType.MERGE, CascadeType.REMOVE},
      orphanRemoval = false)
  private List<CollectionEntry> entries;

  @Column(name = "CREATED_AT")
  @CreatedDate
  private Instant createdAt;

  protected Collection() {}

  private Collection(User user, String name) {
    this.user = user;
    this.name = name;
    this.entries = Collections.emptyList();
  }

  public String getCollectionId() {
    return collectionId;
  }

  public String getName() {
    return name;
  }

  public List<CollectionEntry> getEntries() {
    return entries;
  }

  public static CollectionBuilder builder() {
    return new CollectionBuilder();
  }

  public static class CollectionBuilder {

    private User user;
    private String name;

    public CollectionBuilder user(User user) {
      this.user = user;
      return this;
    }

    public CollectionBuilder name(String name) {
      this.name = name;
      return this;
    }

    public Collection build() {
      Assert.notNull(user, "User must not be null");
      Assert.notNull(name, "Name must not be null");

      return new Collection(user, name);
    }
  }

  public void addJournal(Journal journal) {
    CollectionEntry entry = CollectionEntry.from(this, journal);
    entries.add(entry);
  }
}
