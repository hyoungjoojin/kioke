package io.kioke.feature.collection.domain;

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
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "COLLECTION_TABLE")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Collection {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "COLLECTION_ID")
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "IS_DEFAULT", nullable = false)
  private Boolean isDefault;

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "collection",
      cascade = {CascadeType.MERGE, CascadeType.REMOVE},
      orphanRemoval = false)
  private List<CollectionEntry> journals;

  @Column(name = "CREATED_AT")
  @CreatedDate
  private Instant createdAt;
}
