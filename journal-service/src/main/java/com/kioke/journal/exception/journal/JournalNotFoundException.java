package com.kioke.journal.exception.journal;

public class JournalNotFoundException extends Exception {

  public JournalNotFoundException(String jid) {
    super("Journal with ID " + jid + " could not be found.");
  }
}
