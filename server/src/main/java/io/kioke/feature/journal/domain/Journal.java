package io.kioke.feature.journal.domain;

import io.kioke.feature.page.domain.Page;
import io.kioke.feature.user.domain.User;
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

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "AUTHOR_ID", nullable = false)
  private User author;

  @Column(name = "TITLE", nullable = false)
  private String title;

  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @OneToMany(mappedBy = "journal")
  private List<Page> pages;

  @Column(name = "IS_PUBLIC", nullable = false)
  private boolean isPublic;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "LAST_MODIFIED_AT")
  private Instant lastModifiedAt;

  public String getJournalId() {
    return journalId;
  }

  public void setJournalId(String journalId) {
    this.journalId = journalId;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean getIsPublic() {
    return isPublic;
  }

  public void setIsPublic(boolean isPublic) {
    this.isPublic = isPublic;
  }
}
