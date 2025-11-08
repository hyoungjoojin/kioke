package io.kioke.feature.block.domain.blocks;

import io.kioke.feature.block.domain.Block;
import io.kioke.feature.block.domain.BlockType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GALLERY_BLOCK_TABLE")
@DiscriminatorValue(value = BlockType.Values.GALLERY_BLOCK)
@Getter
@Setter
public class GalleryBlock extends Block {

  @OneToMany private List<ImageBlock> images;
}
