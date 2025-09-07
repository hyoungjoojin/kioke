package io.kioke.feature.media.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "MEDIA_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class Media {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column private String key;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;

  protected Media() {}

  public static Media of(String key) {
    Media media = new Media();
    media.key = key;

    return media;
  }

  public String id() {
    return id;
  }

  public String key() {
    return key;
  }
}
