package com.kioke.journal.exception.journal;

public class JournalNotFoundException extends Exception {
  private String jid;

  public JournalNotFoundException(String jid) {
    this.jid = jid;
  }

  @Override
  public String toString() {
    if (jid == null) {
      return "Requested journal ID is null.";
    }

    return "Journal of ID " + jid + " could not be found.";
  }
}
