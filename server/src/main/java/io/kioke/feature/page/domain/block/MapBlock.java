package io.kioke.feature.page.domain.block;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "MAP_BLOCK_TABLE")
@DiscriminatorValue(value = BlockType.Values.MAP_BLOCK)
public class MapBlock extends Block {

  @OneToMany List<MapBlockMarker> markers;

  @Override
  public BlockType getBlockType() {
    return BlockType.MAP_BLOCK;
  }
}
