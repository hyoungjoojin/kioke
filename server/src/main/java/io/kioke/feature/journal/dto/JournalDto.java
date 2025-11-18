package io.kioke.feature.journal.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.kioke.feature.journal.domain.JournalRole;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record JournalDto(
    @NotNull String id,
    String cover,
    @NotNull List<User> users,
    @NotNull List<Page> pages,
    @NotNull String title,
    @NotNull String description,
    @NotNull boolean isPublic,
    @NotNull Instant createdAt) {

  public static record User(@NotNull String id, @NotNull JournalRole role) {}

  public static record Page(
      @NotNull String id, @NotNull String title, @NotNull LocalDateTime date) {}
}
