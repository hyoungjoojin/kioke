package kioke.journal.dto.request.journal;

import jakarta.validation.constraints.NotNull;

public record CreateJournalRequestBodyDto(
    @NotNull String shelfId, String title, String description) {}
