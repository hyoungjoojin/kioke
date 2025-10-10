package io.kioke.feature.page.dto;

import io.kioke.feature.page.domain.block.BlockType;
import java.util.List;

public record MapBlockContentDto(BlockType type, List<MapMarkerDto> markers)
    implements BlockContentDto {}
