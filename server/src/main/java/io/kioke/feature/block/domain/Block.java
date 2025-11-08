package io.kioke.feature.block.domain;

import io.kioke.feature.page.domain.Page;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "BLOCK_TABLE")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "BLOCK_TYPE", discriminatorType = DiscriminatorType.STRING)
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Block {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String blockId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PAGE_ID")
  private Page page;

  @Enumerated(EnumType.STRING)
  @Column(name = "BLOCK_TYPE", insertable = false, updatable = false)
  private BlockType type;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "LAST_MODIFIED_AT")
  private Instant lastModifiedAt;
}
