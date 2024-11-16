package com.kioke.journal.constant;

public enum ErrorCode {
  JOURNAL_NOT_FOUND("/errors/journal-not-found", "The requested journal could not be found."),
  BAD_REQUEST("", "Bad request."),
  INTERNAL_SERVER_ERROR("", "Internal server error.");

  private final String type;
  private final String title;

  private ErrorCode(String type, String title) {
    this.type = type;
    this.title = title;
  }

  public String getType() {
    return type;
  }

  public String getTitle() {
    return title;
  }
}
