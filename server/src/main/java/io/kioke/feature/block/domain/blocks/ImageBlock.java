package io.kioke.feature.block.domain.blocks;

import io.kioke.feature.block.domain.Block;
import io.kioke.feature.block.domain.BlockType;
import io.kioke.feature.image.domain.Image;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "IMAGE_BLOCK_TABLE")
@DiscriminatorValue(value = BlockType.Values.IMAGE_BLOCK)
@Getter
@Setter
public class ImageBlock extends Block {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "GALLERY_BLOCK_ID")
  private GalleryBlock parent;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "IMAGE_ID")
  private Image image;

  @Column(name = "DESCRIPTION")
  private String description;

  public static ImageBlock of(String blockId) {
    ImageBlock block = new ImageBlock();
    block.setBlockId(blockId);
    return block;
  }
}
