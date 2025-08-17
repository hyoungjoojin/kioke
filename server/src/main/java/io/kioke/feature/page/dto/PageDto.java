package io.kioke.feature.page.dto;

import java.time.LocalDateTime;

public record PageDto(
    String pageId, String journalId, String title, String content, LocalDateTime date) {}
