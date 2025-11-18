package io.kioke.feature.journal.domain;

import io.kioke.feature.image.domain.Image;
import io.kioke.feature.page.domain.Page;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "JOURNAL_TABLE")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Journal {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "JOURNAL_ID")
  private String id;

  @OneToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
      orphanRemoval = true,
      mappedBy = "journal")
  private List<JournalUser> users;

  @Column(name = "IS_PUBLIC", nullable = false)
  private Boolean isPublic;

  @Column(name = "TITLE", nullable = false)
  private String title;

  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "COVER_IMAGE_ID")
  private Image coverImage;

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
}
