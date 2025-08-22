package io.kioke.feature.collection.dto.response;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record GetCollectionsResponseDto(
    int count, @NotNull List<GetCollectionResponseDto> collections) {}
