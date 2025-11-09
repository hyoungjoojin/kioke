package io.kioke.feature.block.domain.blocks;

import io.kioke.feature.block.domain.Block;
import io.kioke.feature.block.domain.BlockType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "MAP_BLOCK_TABLE")
@DiscriminatorValue(value = BlockType.Values.MAP_BLOCK)
public class MapBlock extends Block {

  @OneToMany(mappedBy = "parent")
  private List<MarkerBlock> markers;
}
