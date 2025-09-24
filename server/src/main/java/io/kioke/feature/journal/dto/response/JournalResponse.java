package io.kioke.feature.journal.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.kioke.feature.journal.domain.JournalRole;
import io.kioke.feature.journal.domain.JournalType;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record JournalResponse(
    @NotNull String id,
    List<User> users,
    @NotNull JournalType type,
    String title,
    String description,
    String coverUrl,
    List<Page> pages,
    boolean isPublic,
    Instant createdAt) {

  public static record Page(String id, String title, LocalDateTime date) {}

  public static record User(String userId, JournalRole role) {}
}
