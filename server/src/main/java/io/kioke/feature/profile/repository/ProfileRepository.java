package io.kioke.feature.profile.repository;

import io.kioke.feature.profile.domain.Profile;
import io.kioke.feature.profile.dto.projection.MyProfileProjection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {

  @Query(
      """
      SELECT
        p.userId, u.email, p.name, p.onboarded, p.createdAt
      FROM
        Profile p
        JOIN p.user u ON p.userId = u.userId
      WHERE p.userId = :userId
      """)
  public Optional<MyProfileProjection> findMyProfileByUserId(String userId);

  @Query("SELECT p from Profile p JOIN FETCH p.user WHERE p.userId = :userId")
  public Optional<Profile> findById(String userId);

  @Query("SELECT p from Profile p JOIN FETCH p.user " + "WHERE p.user.email = :query")
  public List<Profile> findByQuery(String query);
}
