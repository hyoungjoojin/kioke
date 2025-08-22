package io.kioke.feature.collection.dto.response;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record GetCollectionResponseDto(
    @NotNull String id, @NotNull String name, @NotNull List<Journal> journals) {

  public static record Journal(@NotNull String id, String title) {}
}
