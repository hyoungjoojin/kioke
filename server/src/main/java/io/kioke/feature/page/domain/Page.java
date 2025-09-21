package io.kioke.feature.page.domain;

import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.page.domain.block.Block;
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
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "PAGE_TABLE")
@EntityListeners(AuditingEntityListener.class)
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

  @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "page")
  private List<Block> blocks;

  @Column(name = "DATE")
  private LocalDateTime date;

  @Column(name = "CREATED_AT")
  @CreatedDate
  private Instant createdAt;

  @Column(name = "LAST_MODIFIED_AT")
  @LastModifiedDate
  private Instant lastModifiedAt;

  public String getPageId() {
    return pageId;
  }

  public Journal getJournal() {
    return journal;
  }

  public String getTitle() {
    return title;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public List<Block> getBlocks() {
    return blocks;
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
      Page page = new Page();
      page.journal = journal;
      page.title = title;
      page.date = date;
      return page;
    }
  }

  public void updateTitle(String title) {
    this.title = title;
  }

  public void updateDate(LocalDateTime date) {
    this.date = date;
  }
}
