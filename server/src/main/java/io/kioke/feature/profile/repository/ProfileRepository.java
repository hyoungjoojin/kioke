package io.kioke.feature.profile.repository;

import io.kioke.feature.profile.domain.Profile;
import io.kioke.feature.profile.dto.ProfileDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {

  @Query(
      "SELECT u.email, p.name, p.onboarded, p.createdAt, p.lastModifiedAt "
          + "FROM Profile p JOIN p.user u "
          + "WHERE p.userId = :userId")
  public Optional<ProfileDto> findByUserId(String userId);
}
