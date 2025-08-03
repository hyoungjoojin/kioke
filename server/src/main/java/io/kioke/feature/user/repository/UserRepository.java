package io.kioke.feature.user.repository;

import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query("SELECT u.userId FROM User u WHERE u.userId = :userId")
  public Optional<UserDto> findUserById(String userId);

  @Query("SELECT u.userId FROM User u WHERE u.email = :email")
  public Optional<UserDto> findUserByEmail(String email);
}
