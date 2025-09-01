package io.kioke.feature.profile.repository;

import io.kioke.feature.profile.domain.Profile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {

  @Query("SELECT p from Profile p JOIN FETCH p.user WHERE p.userId = :userId")
  public Optional<Profile> findById(String userId);

  @Query(
      "SELECT p from Profile p JOIN FETCH p.user "
          + "WHERE (p.user.email LIKE CONCAT(:query, '%') OR p.name LIKE CONCAT(:query, '%')) "
          + "AND p.onboarded = TRUE")
  public List<Profile> findByQuery(String query);
}
