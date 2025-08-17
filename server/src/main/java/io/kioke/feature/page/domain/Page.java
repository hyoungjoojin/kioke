package io.kioke.feature.page.domain;

import io.kioke.feature.journal.domain.Journal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAGE_TABLE")
public class Page {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "PAGE_ID")
  private String pageId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "JOURNAL_ID", nullable = false)
  private Journal journal;

  @Column(name = "TITLE")
  private String title;

  @Lob
  @Column(name = "CONTENT", columnDefinition = "jsonb")
  private String content;

  @Column(name = "DATE")
  private LocalDateTime date;

  public String getPageId() {
    return pageId;
  }

  public Journal getJournal() {
    return journal;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public void setJournal(Journal journal) {
    this.journal = journal;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }
}
