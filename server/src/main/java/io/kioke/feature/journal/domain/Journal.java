package io.kioke.feature.journal.domain;

import io.kioke.feature.page.domain.Page;
import io.kioke.feature.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

@Entity
@Table(name = "JOURNAL_TABLE")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Journal {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "JOURNAL_ID")
  private String journalId;

  @OneToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  private JournalCoverImage coverImage;

  @OneToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
      orphanRemoval = true,
      mappedBy = "journal")
  private List<JournalUser> users;

  @Column(name = "IS_DELETED")
  private boolean isDeleted = false;

  @Column(name = "IS_PUBLIC", nullable = false)
  private boolean isPublic = false;

  @Enumerated(EnumType.STRING)
  @Column(name = "JOURNAL_TYPE", nullable = false)
  private JournalType type;

  @Column(name = "TITLE", nullable = false)
  private String title;

  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "journal",
      cascade = {CascadeType.REMOVE})
  private List<Page> pages;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "LAST_MODIFIED_AT")
  private Instant lastModifiedAt;

  public static Journal getReferenceById(String journalId) {
    Journal journal = new Journal();
    journal.setJournalId(journalId);
    return journal;
  }

  @Column(name = "DELETED_AT")
  private Instant deletedAt;

  public static JournalBuilder builder() {
    return new JournalBuilder();
  }

  public static class JournalBuilder {

    private JournalType type;
    private String title;

    public JournalBuilder type(JournalType type) {
      this.type = type;
      return this;
    }

    public JournalBuilder title(String title) {
      this.title = title;
      return this;
    }

    public Journal build() {
      Assert.notNull(type, "The type of the journal must not be null");
      Assert.notNull(title, "The title of the journal must not be null");

      Journal journal = new Journal();
      journal.type = type;
      journal.title = title;
      journal.description = "";
      journal.users = new ArrayList<>();
      return journal;
    }
  }

  public void addAuthor(User user) {
    users.add(JournalUser.builder().user(user).journal(this).role(JournalRole.AUTHOR).build());
  }

  public void updateTitle(String title) {
    this.title = title;
  }

  public void updateDescription(String description) {
    this.description = description;
  }

  public void updateCover(JournalCoverImage coverImage) {
    this.coverImage = coverImage;
  }

  public void updateType(JournalType type) {
    this.type = type;
  }

  public void deleteJournal() {
    this.isDeleted = true;
    this.deletedAt = Instant.now();
  }
}
