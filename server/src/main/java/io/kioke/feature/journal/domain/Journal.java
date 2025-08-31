package io.kioke.feature.journal.domain;

import io.kioke.feature.journal.constant.JournalType;
import io.kioke.feature.page.domain.Page;
import io.kioke.feature.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "JOURNAL_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class Journal {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "JOURNAL_ID")
  private String journalId;

  @Enumerated(EnumType.STRING)
  @Column(name = "JOURNAL_TYPE", nullable = false)
  private JournalType type;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "AUTHOR_ID", nullable = false)
  private User author;

  @Column(name = "TITLE", nullable = false)
  private String title;

  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @Column(name = "IS_PUBLIC", nullable = false)
  private boolean isPublic;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "journal")
  private List<Page> pages;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "LAST_MODIFIED_AT")
  private Instant lastModifiedAt;

  protected Journal() {}

  private Journal(String journalId) {
    this.journalId = journalId;
  }

  private Journal(JournalType journalType, User author, String title) {
    this.type = journalType;
    this.author = author;
    this.title = title;
    this.description = "";
    this.isPublic = false;
    this.pages = new ArrayList<>();
  }

  public String getJournalId() {
    return journalId;
  }

  public JournalType getType() {
    return type;
  }

  public User getAuthor() {
    return author;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public boolean getIsPublic() {
    return isPublic;
  }

  public List<Page> getPages() {
    return pages;
  }

  public static Journal from(String journalId) {
    return new Journal(journalId);
  }

  public static JournalBuilder builder() {
    return new JournalBuilder();
  }

  public static class JournalBuilder {

    private JournalType type;
    private User author;
    private String title;

    public JournalBuilder type(JournalType type) {
      this.type = type;
      return this;
    }

    public JournalBuilder author(User author) {
      this.author = author;
      return this;
    }

    public JournalBuilder title(String title) {
      this.title = title;
      return this;
    }

    public Journal build() {
      return new Journal(type, author, title);
    }
  }

  public void setType(JournalType type) {
    this.type = type;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setIsPublic(boolean isPublic) {
    this.isPublic = isPublic;
  }
}
