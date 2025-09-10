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
  @Column(name = "CONTENT")
  private String content;

  @Column(name = "DATE")
  private LocalDateTime date;

  protected Page() {}

  private Page(Journal journal, String title, LocalDateTime date) {
    this.journal = journal;
    this.title = title;
    this.content = "";
    this.date = date;
  }

  public static Page createReference(String pageId) {
    Page page = new Page();
    page.pageId = pageId;
    return page;
  }

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

  public static PageBuilder builder() {
    return new PageBuilder();
  }

  public static class PageBuilder {

    private Journal journal;
    private String title;
    private LocalDateTime date;

    public PageBuilder journal(Journal journal) {
      this.journal = journal;
      return this;
    }

    public PageBuilder title(String title) {
      this.title = title;
      return this;
    }

    public PageBuilder date(LocalDateTime date) {
      this.date = date;
      return this;
    }

    public Page build() {
      return new Page(journal, title, date);
    }
  }

  public void changeTitle(String title) {
    this.title = title;
  }

  public void changeContent(String content) {
    this.content = content;
  }

  public void changeDate(LocalDateTime date) {
    this.date = date;
  }
}
