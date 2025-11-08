package io.kioke.feature.page.dto;

import io.kioke.feature.block.dto.BlockDto;
import java.time.LocalDateTime;
import java.util.List;

public record PageDto(
    String pageId, String journalId, String title, LocalDateTime date, List<BlockDto> blocks) {}
