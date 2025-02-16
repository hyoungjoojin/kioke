package com.kioke.journal.exception.shelf;

public class ShelfNotFoundException extends Exception {

  public ShelfNotFoundException(String shelfId) {
    super("Shelf with ID " + shelfId + " could not be found.");
  }
}
