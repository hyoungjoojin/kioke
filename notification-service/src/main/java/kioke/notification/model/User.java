package kioke.notification.model;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USER_TABLE")
@Data
@Builder
public class User implements UserDetails {

  @Id private String userId;

  @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "issuedTo")
  private List<Notification> issuedNotifications;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public String getUsername() {
    return userId;
  }

  @Override
  public String getPassword() {
    return "";
  }
}
