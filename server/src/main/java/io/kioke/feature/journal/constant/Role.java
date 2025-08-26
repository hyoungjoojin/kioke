package io.kioke.feature.journal.constant;

public enum Role {
  AUTHOR,
  EDITOR,
  VIEWER,
  NONE;

  public boolean canRead() {
    if (this.equals(Role.AUTHOR) || this.equals(Role.EDITOR) || this.equals(Role.VIEWER)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean canEdit() {
    if (this.equals(Role.AUTHOR) || this.equals(Role.EDITOR)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean canShare() {
    if (this.equals(Role.AUTHOR)) {
      return true;
    } else {
      return false;
    }
  }
}
