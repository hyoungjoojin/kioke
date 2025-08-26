package io.kioke.feature.journal.dto;

import io.kioke.feature.journal.constant.Role;
import java.util.List;

public record JournalDto(
    String id,
    String title,
    String description,
    boolean isPublic,
    List<Page> pages,
    Role role,
    List<User> collaborators) {

  public static record Page(String id, String title) {}

  public static record User(String userId, Role role) {}
}
