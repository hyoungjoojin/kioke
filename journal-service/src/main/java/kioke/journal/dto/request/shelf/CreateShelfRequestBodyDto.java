package kioke.journal.dto.request.shelf;

import jakarta.validation.constraints.NotNull;

public record CreateShelfRequestBodyDto(@NotNull String name) {}
