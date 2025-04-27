package kioke.journal.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import kioke.journal.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "USER_JOURNAL_METATDATA_TABLE",
    indexes = {@Index(columnList = "USER_ID, JOURNAL_ID")})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserJournalMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.REMOVE},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.REMOVE},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "JOURNAL_ID", nullable = false)
  private Journal journal;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(nullable = false)
  private boolean isBookmarked;

  @Column(nullable = false)
  private Instant lastViewed;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @LastModifiedDate
  @Column(nullable = false)
  private Instant lastModified;

  public String getUserId() {
    return user.getUserId();
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public boolean isBookmarked() {
    return isBookmarked;
  }

  public void setIsBookmarked(boolean isBookmarked) {
    this.isBookmarked = isBookmarked;
  }

  public void setLastViewed(Instant lastViewed) {
    this.lastViewed = lastViewed;
  }
}
