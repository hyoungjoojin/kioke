package io.kioke.feature.page.domain.block;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "TEXT_BLOCK_TABLE")
@DiscriminatorValue(value = BlockType.Values.TEXT_BLOCK)
public class TextBlock extends Block {

  @Lob
  @Column(name = "TEXT")
  private String text;

  @Override
  public BlockType getBlockType() {
    return BlockType.TEXT_BLOCK;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
