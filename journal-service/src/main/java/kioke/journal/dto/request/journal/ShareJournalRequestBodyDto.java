package kioke.journal.dto.request.journal;

import jakarta.validation.constraints.NotNull;
import kioke.journal.constant.Role;

public record ShareJournalRequestBodyDto(@NotNull String userId, @NotNull Role role) {}
