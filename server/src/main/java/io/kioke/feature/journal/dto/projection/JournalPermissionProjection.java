package io.kioke.feature.journal.dto.projection;

import io.kioke.feature.journal.domain.JournalRole;

public record JournalPermissionProjection(boolean isPublic, JournalRole role) {}
