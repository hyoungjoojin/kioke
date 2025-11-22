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
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "journals")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Journal {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private String id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description", nullable = false)
  private String description;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cover_image")
  private Image cover;

  @Column(name = "is_public", nullable = false)
  @Default
  private Boolean isPublic = false;

  @CreatedDate
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "last_modified_at", nullable = false)
  private Instant lastModifiedAt;

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "journal",
      cascade = {CascadeType.REMOVE})
  private List<Page> pages;

  @OneToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
      orphanRemoval = true,
      mappedBy = "journal")
  private List<JournalUser> users;
}
