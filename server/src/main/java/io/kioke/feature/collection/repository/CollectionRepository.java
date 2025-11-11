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

  @Query("SELECT c FROM Collection c LEFT JOIN FETCH c.journals WHERE c.user = :user")
  public List<Collection> findAllByUser(User user);

  @Query("SELECT c FROM Collection c WHERE c.user = :user AND c.isDefault = true")
  public Optional<Collection> findDefaultCollectionByUser(User user);

  public long countByUser(User user);
}
