package io.kioke.feature.journal.dto.response;

import io.kioke.feature.journal.constant.Role;
import java.util.List;

public record GetJournalResponseDto(
    String id,
    String title,
    String description,
    List<Page> pages,
    Boolean isPublic,
    Role role,
    List<User> collaborators) {

  public static record Page(String id, String title) {}

  public static record User(String userId, Role role) {}
}
