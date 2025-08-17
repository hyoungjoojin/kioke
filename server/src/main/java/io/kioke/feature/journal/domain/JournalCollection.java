package io.kioke.feature.journal.domain;

import io.kioke.feature.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "JOURNAL_COLLECTION_TABLE")
public class JournalCollection {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "COLLECTION_ID")
  private String collectionId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  @Column(name = "NAME", nullable = false)
  private String name;

  @ManyToMany
  @JoinTable(
      name = "JOURNAL_COLLECTION_ENTRY_TABLE",
      joinColumns = @JoinColumn(name = "collectionId", referencedColumnName = "COLLECTION_ID"),
      inverseJoinColumns = @JoinColumn(name = "journalId", referencedColumnName = "JOURNAL_ID"))
  private List<Journal> journals;

  @Column(name = "IS_DEFAULT_COLLECTION", nullable = false)
  private boolean isDefaultCollection;

  public String getCollectionId() {
    return collectionId;
  }

  public String getName() {
    return name;
  }

  public boolean getIsDefaultCollection() {
    return isDefaultCollection;
  }

  public void setCollectionId(String collectionId) {
    this.collectionId = collectionId;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIsDefaultCollection(boolean isDefaultCollection) {
    this.isDefaultCollection = isDefaultCollection;
  }

  public List<Journal> getJournals() {
    return journals;
  }
}
