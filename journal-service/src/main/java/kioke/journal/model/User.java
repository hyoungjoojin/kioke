package kioke.journal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USER_TABLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
  @Id private String uid;

  @OneToMany(mappedBy = "owner")
  private List<Shelf> shelves;

  @OneToMany(mappedBy = "user")
  private List<ShelfSlot> shelfSlots;

  @OneToMany(mappedBy = "user")
  private List<JournalRole> journals;

  @OneToMany(mappedBy = "user")
  private List<Bookmark> bookmarks;

  @Override
  public String getUsername() {
    return uid;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }
}
