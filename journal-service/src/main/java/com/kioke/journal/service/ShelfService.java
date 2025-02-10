package com.kioke.journal.service;

import com.kioke.journal.exception.shelf.ShelfNotFoundException;
import com.kioke.journal.model.Shelf;
import com.kioke.journal.model.User;
import com.kioke.journal.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ShelfService {
  @Autowired @Lazy private ShelfRepository shelfRepository;

  public Shelf createShelf(User user, String name) {
    Shelf shelf = Shelf.builder().name(name).owner(user).build();
    shelf = shelfRepository.save(shelf);
    return shelf;
  }

  public Shelf getShelfById(String shelfId) throws ShelfNotFoundException {
    return shelfRepository.findById(shelfId).orElseThrow(() -> new ShelfNotFoundException(shelfId));
  }
}
