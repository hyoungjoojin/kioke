package io.kioke.feature.journal.dto.response;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record GetJournalCollectionsResponseDto(
    @NotNull List<GetJournalCollectionResponseDto> collections) {}
