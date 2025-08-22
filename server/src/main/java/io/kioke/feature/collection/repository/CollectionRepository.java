package io.kioke.feature.collection.repository;

import io.kioke.feature.collection.domain.Collection;
import io.kioke.feature.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, String> {

  @Query(
      "SELECT c FROM Collection c LEFT JOIN FETCH c.entries "
          + "WHERE c.user = :user AND c.collectionId = :collectionId")
  public Optional<Collection> findByUserAndId(User user, String collectionId);

  @Query("SELECT c FROM Collection c LEFT JOIN FETCH c.entries WHERE c.user = :user")
  public List<Collection> findByUser(User user);
}
