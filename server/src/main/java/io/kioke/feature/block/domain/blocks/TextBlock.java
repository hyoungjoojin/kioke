package io.kioke.feature.block.domain.blocks;

import io.kioke.feature.block.domain.Block;
import io.kioke.feature.block.domain.BlockType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TEXT_BLOCK_TABLE")
@DiscriminatorValue(value = BlockType.Values.TEXT_BLOCK)
@Getter
@Setter
public class TextBlock extends Block {

  @Lob
  @Column(name = "TEXT")
  private String text;
}
