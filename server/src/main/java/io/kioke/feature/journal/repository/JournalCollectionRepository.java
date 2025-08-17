package io.kioke.feature.journal.repository;

import io.kioke.feature.journal.domain.JournalCollection;
import io.kioke.feature.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JournalCollectionRepository extends JpaRepository<JournalCollection, String> {

  @Query(
      "SELECT COUNT(j) > 0 FROM JournalCollection j "
          + "WHERE j.user = :user AND j.isDefaultCollection = true")
  public Boolean existsDefaultCollection(User user);
}
