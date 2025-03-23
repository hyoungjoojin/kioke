package kioke.user.repository;

import java.util.Optional;
import kioke.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
  public Optional<User> findByEmail(String email);
}
