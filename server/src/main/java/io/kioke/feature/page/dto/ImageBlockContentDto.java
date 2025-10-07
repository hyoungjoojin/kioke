package io.kioke.feature.page.dto;

import io.kioke.feature.page.domain.block.BlockType;
import java.util.List;

public record ImageBlockContentDto(BlockType type, List<PageImageDto> images)
    implements BlockContentDto {}
