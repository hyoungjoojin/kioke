package io.kioke.feature.page.service;

import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.domain.block.MapBlock;
import io.kioke.feature.page.domain.block.MapBlockMarker;
import io.kioke.feature.page.dto.BlockContentDto;
import io.kioke.feature.page.dto.BlockDto;
import io.kioke.feature.page.dto.MapBlockContentDto;
import io.kioke.feature.page.dto.MapMarkerDto;
import io.kioke.feature.user.domain.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
class MapBlockProcessor implements BlockProcessor {

  @Override
  public BlockType type() {
    return BlockType.MAP_BLOCK;
  }

  @Override
  public BlockDto map(Block block) {
    MapBlock mapBlock = (MapBlock) block;

    List<MapMarkerDto> markers = mapBlock.getMarkers().stream().map(this::map).toList();

    MapBlockContentDto content = new MapBlockContentDto(BlockType.MAP_BLOCK, markers);
    return new BlockDto(block.getBlockId(), content);
  }

  @Override
  public Block createBlock(User requester, BlockContentDto content) {
    MapBlock mapBlock = new MapBlock();
    updateBlock(requester, mapBlock, content);
    return mapBlock;
  }

  @Override
  public Block updateBlock(User requester, Block block, BlockContentDto content) {
    MapBlock mapBlock = new MapBlock();
    MapBlockContentDto mapBlockContent = (MapBlockContentDto) content;
    return mapBlock;
  }

  private MapMarkerDto map(MapBlockMarker marker) {
    return new MapMarkerDto(
        marker.getId(),
        marker.getLatitude(),
        marker.getLongitude(),
        marker.getTitle(),
        marker.getDescription());
  }
}
