package io.kioke.feature.collection.repository;

import io.kioke.feature.collection.domain.Collection;
import io.kioke.feature.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, String> {

  @Query("SELECT c FROM Collection c LEFT JOIN FETCH c.journals WHERE c.user = :user")
  public List<Collection> findAllByUser(User user);
}
