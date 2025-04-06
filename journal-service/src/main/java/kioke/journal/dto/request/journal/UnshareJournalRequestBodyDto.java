package kioke.journal.dto.request.journal;

import jakarta.validation.constraints.NotNull;

public record UnshareJournalRequestBodyDto(@NotNull String userId) {}
