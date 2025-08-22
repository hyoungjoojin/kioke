package io.kioke.feature.user.repository;

import io.kioke.feature.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  public Optional<User> findByEmail(String email);
}
