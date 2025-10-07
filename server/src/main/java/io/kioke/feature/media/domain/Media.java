package io.kioke.feature.media.domain;

import io.kioke.feature.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Instant;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Media {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "MEDIA_KEY", unique = true, nullable = false)
  private String key;

  @Column(name = "NAME")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "UPLOADER_ID")
  private User uploader;

  @Column(name = "IS_PENDING")
  private boolean isPending = true;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;
}
