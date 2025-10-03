package io.kioke.feature.journal.dto.request;

import io.kioke.feature.journal.domain.JournalType;

public record UpdateJournalRequest(
    String title, String description, String coverImage, JournalType type) {}
