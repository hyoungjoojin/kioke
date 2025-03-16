package com.kioke.journal.service;

import com.kioke.journal.exception.shelf.ShelfNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.model.Shelf;
import com.kioke.journal.model.ShelfSlot;
import com.kioke.journal.model.User;
import com.kioke.journal.repository.ShelfRepository;
import com.kioke.journal.repository.ShelfSlotRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ShelfService {
  @Autowired @Lazy private ShelfRepository shelfRepository;
  @Autowired @Lazy private ShelfSlotRepository shelfSlotRepository;

  public Shelf createShelf(User user, String name) {
    Shelf shelf = Shelf.builder().name(name).owner(user).isArchive(false).build();
    shelf = shelfRepository.save(shelf);
    return shelf;
  }

  public Shelf createArchive(User user) {
    Shelf shelf = Shelf.builder().name("Archive").owner(user).isArchive(true).build();
    shelf = shelfRepository.save(shelf);
    return shelf;
  }

  public Shelf getShelfById(String shelfId) throws ShelfNotFoundException {
    return shelfRepository.findById(shelfId).orElseThrow(() -> new ShelfNotFoundException());
  }

  public List<Shelf> getShelves(User user) {
    return user.getShelves();
  }

  public Shelf getArchive(User user) {
    Shelf archive =
        shelfRepository
            .findByOwnerAndIsArchiveTrue(user)
            .orElseThrow(() -> new IllegalStateException("User does not have archive shelf."));

    return archive;
  }

  public void updateShelf(Shelf shelf, String name) {
    if (name != null && !shelf.isArchive()) {
      shelf.setName(name);
    }

    shelf = shelfRepository.save(shelf);
  }

  public void putJournalInShelf(Journal journal, Shelf shelf) {
    User user = shelf.getOwner();
    Optional<ShelfSlot> existingShelfSlot = shelfSlotRepository.findByUserAndJournal(user, journal);

    ShelfSlot shelfSlot;
    if (existingShelfSlot.isPresent()) {
      shelfSlot = existingShelfSlot.get();
      shelfSlot.setShelf(shelf);
    } else {
      shelfSlot = ShelfSlot.builder().user(user).shelf(shelf).journal(journal).build();
    }

    shelfSlotRepository.save(shelfSlot);
  }
}
