package io.kioke.feature.media.domain;

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
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners(AuditingEntityListener.class)
public abstract class Media {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "MEDIA_KEY", nullable = false)
  private String key;

  @Column(name = "MEDIA_CONTEXT", nullable = true)
  @Enumerated(EnumType.STRING)
  private MediaContext mediaContext;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "UPLOADER_ID")
  private User uploader;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;

  public String getId() {
    return id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public User getUploader() {
    return uploader;
  }

  public void setUploader(User uploader) {
    this.uploader = uploader;
  }

  public void setContext(MediaContext mediaContext) {
    this.mediaContext = mediaContext;
  }

  public boolean isPending() {
    return mediaContext == null;
  }
}
