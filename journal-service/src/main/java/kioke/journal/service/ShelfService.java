package kioke.journal.service;

import java.util.List;
import kioke.journal.exception.shelf.ShelfNotFoundException;
import kioke.journal.model.Journal;
import kioke.journal.model.Shelf;
import kioke.journal.model.ShelfSlot;
import kioke.journal.model.User;
import kioke.journal.repository.ShelfRepository;
import kioke.journal.repository.ShelfSlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ShelfService {

  private final UserService userService;

  private final ShelfRepository shelfRepository;
  private final ShelfSlotRepository shelfSlotRepository;

  public ShelfService(
      UserService userService,
      ShelfRepository shelfRepository,
      ShelfSlotRepository shelfSlotRepository) {
    this.userService = userService;
    this.shelfRepository = shelfRepository;
    this.shelfSlotRepository = shelfSlotRepository;
  }

  @Transactional(readOnly = true)
  public List<Shelf> getShelves(String userId) {
    User user = userService.getUserById(userId);
    return user.getShelves();
  }

  @Transactional(readOnly = true)
  public Shelf getShelfById(String userId, String shelfId) throws ShelfNotFoundException {
    Shelf shelf =
        shelfRepository
            .findById(shelfId)
            .orElseThrow(
                () -> {
                  log.debug("Shelf could not be found in the database.");
                  return new ShelfNotFoundException(shelfId);
                });

    if (!shelf.getOwner().getUserId().equals(userId)) {
      log.debug("User has no permission to read the shelf.");
      throw new ShelfNotFoundException(shelfId);
    }

    return shelf;
  }

  @Transactional(readOnly = true)
  public Shelf getArchiveShelf(User user) {
    Shelf archive =
        shelfRepository
            .findByOwnerAndIsArchiveTrue(user)
            .orElseThrow(() -> new IllegalStateException("User does not have archive shelf."));

    return archive;
  }

  @Transactional
  public void setShelf(User user, Journal journal, Shelf shelf) {
    ShelfSlot shelfSlot =
        shelfSlotRepository
            .findByUserAndJournal(user, journal)
            .orElse(ShelfSlot.builder().user(user).shelf(null).journal(journal).build());

    if (!shelf.equals(shelfSlot.getShelf())) {
      shelfSlot.setShelf(shelf);
      shelfSlotRepository.save(shelfSlot);
    }
  }

  @Transactional
  public Shelf createShelf(String userId, String name) {
    User user = userService.getUserById(userId);

    Shelf shelf = Shelf.builder().name(name).owner(user).isArchive(false).build();
    shelf = shelfRepository.save(shelf);
    return shelf;
  }

  @Transactional
  public Shelf createArchive(User user) {
    Shelf shelf = Shelf.builder().name("Archive").owner(user).isArchive(true).build();
    shelf = shelfRepository.save(shelf);
    return shelf;
  }

  @Transactional
  public void updateShelf(String userId, String shelfId, String name)
      throws ShelfNotFoundException {
    Shelf shelf = getShelfById(userId, shelfId);

    if (name != null && !shelf.isArchive()) {
      shelf.setName(name);
    }

    shelfRepository.save(shelf);
  }
}
