package io.kioke.feature.page.dto.request;

import java.time.LocalDateTime;

public record UpdatePageRequest(String title, LocalDateTime date) {}
