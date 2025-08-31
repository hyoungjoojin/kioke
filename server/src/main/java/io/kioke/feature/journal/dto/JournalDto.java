package io.kioke.feature.journal.dto;

import io.kioke.feature.journal.constant.JournalType;
import io.kioke.feature.journal.constant.Role;
import java.time.LocalDateTime;
import java.util.List;

public record JournalDto(
    String id,
    JournalType type,
    String title,
    String description,
    boolean isPublic,
    List<Page> pages,
    Role role,
    List<User> collaborators) {

  public static record Page(String id, String title, LocalDateTime date) {}

  public static record User(String userId, Role role) {}
}
