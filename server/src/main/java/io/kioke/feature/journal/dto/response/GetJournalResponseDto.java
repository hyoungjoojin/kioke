package io.kioke.feature.journal.dto.response;

import io.kioke.feature.journal.constant.JournalType;
import io.kioke.feature.journal.constant.Role;
import java.time.LocalDateTime;
import java.util.List;

public record GetJournalResponseDto(
    String id,
    JournalType type,
    String title,
    String description,
    List<Page> pages,
    Boolean isPublic,
    Role role,
    List<User> collaborators) {

  public static record Page(String id, String title, LocalDateTime date) {}

  public static record User(String userId, Role role) {}
}
