package com.kioke.journal.repository;

import com.kioke.journal.model.Shelf;
import com.kioke.journal.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShelfRepository extends JpaRepository<Shelf, String> {
  @Query(value = "SELECT s FROM Shelf s WHERE s.owner = :user AND s.isArchive = TRUE")
  public Optional<Shelf> findArchive(@Param("user") User user);
}
