package com.kioke.journal.repository;

import com.kioke.journal.model.Journal;
import com.kioke.journal.model.ShelfSlot;
import com.kioke.journal.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfSlotRepository extends JpaRepository<ShelfSlot, String> {
  public Optional<ShelfSlot> findByUserAndJournal(User user, Journal journal);
}
