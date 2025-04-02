package kioke.journal.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BOOKMARK_TABLE")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Bookmark {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "USER_ID")
  private User user;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "JOURNAL_ID")
  private Journal journal;
}
