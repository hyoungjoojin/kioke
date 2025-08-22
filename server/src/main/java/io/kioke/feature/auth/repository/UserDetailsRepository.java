package io.kioke.feature.auth.repository;

import io.kioke.feature.auth.domain.UserDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {

  @Query("SELECT u FROM UserDetails u JOIN u.user WHERE u.user.email = :email")
  public Optional<UserDetails> findByEmail(String email);
}
