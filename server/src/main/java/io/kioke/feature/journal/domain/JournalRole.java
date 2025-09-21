package io.kioke.feature.journal.domain;

public enum JournalRole {
  AUTHOR,
  EDITOR,
  VIEWER;

  public boolean canRead() {
    return true;
  }

  public boolean canEdit() {
    if (this.equals(JournalRole.VIEWER)) {
      return false;
    }

    return true;
  }

  public boolean canDelete() {
    if (this.equals(JournalRole.AUTHOR)) {
      return true;
    }

    return false;
  }

  public static class Values {
    public static final String AUTHOR = "AUTHOR";
  }
}
