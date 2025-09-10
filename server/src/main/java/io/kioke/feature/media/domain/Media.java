package io.kioke.feature.media.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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

  @Column private String key;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;

  protected Media() {}

  public Media(String key) {
    this.key = key;
  }

  public String id() {
    return id;
  }

  public String key() {
    return key;
  }
}
