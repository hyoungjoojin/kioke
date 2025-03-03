package com.kioke.journal.repository;

import com.kioke.journal.model.Shelf;
import com.kioke.journal.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfRepository extends JpaRepository<Shelf, String> {
  public Optional<Shelf> findByOwnerAndIsArchiveTrue(User user);
}
