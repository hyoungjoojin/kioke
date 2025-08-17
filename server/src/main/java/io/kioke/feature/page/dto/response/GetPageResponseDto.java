package io.kioke.feature.page.dto.response;

import java.time.LocalDateTime;

public record GetPageResponseDto(
    String pageId, String journalId, String title, String content, LocalDateTime date) {}
