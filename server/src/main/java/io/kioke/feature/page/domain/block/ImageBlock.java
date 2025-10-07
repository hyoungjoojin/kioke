package io.kioke.feature.page.domain.block;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "IMAGE_BLOCK_TABLE")
@DiscriminatorValue(value = BlockType.Values.IMAGE_BLOCK)
public class ImageBlock extends Block {

  @OneToMany(
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      orphanRemoval = true,
      mappedBy = "imageBlock")
  private List<ImageBlockImage> images;

  @Override
  public BlockType getBlockType() {
    return BlockType.IMAGE_BLOCK;
  }

  public List<ImageBlockImage> getImages() {
    return images;
  }

  public void setImages(List<ImageBlockImage> images) {
    this.images = images;
  }
}
