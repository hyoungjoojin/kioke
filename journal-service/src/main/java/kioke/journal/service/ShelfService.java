package kioke.journal.service;

import java.util.List;
import java.util.Optional;
import kioke.journal.exception.shelf.ShelfNotFoundException;
import kioke.journal.model.Journal;
import kioke.journal.model.Shelf;
import kioke.journal.model.ShelfSlot;
import kioke.journal.model.User;
import kioke.journal.repository.ShelfRepository;
import kioke.journal.repository.ShelfSlotRepository;
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
