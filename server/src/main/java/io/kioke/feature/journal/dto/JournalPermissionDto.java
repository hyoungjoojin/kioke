package io.kioke.feature.journal.dto;

public record JournalPermissionDto(
    boolean isJournalPublic, boolean canRead, boolean canEdit, boolean canDelete) {

  public JournalPermissionDto() {
    this(false, false, false, false);
  }
}
