package io.kioke.feature.block.domain.blocks;

import io.kioke.feature.block.domain.Block;
import io.kioke.feature.block.domain.BlockType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MARKER_BLOCK_TABLE")
@DiscriminatorValue(value = BlockType.Values.MARKER_BLOCK)
@Getter
@Setter
public class MarkerBlock extends Block {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MAP_BLOCK_ID")
  private MapBlock parent;

  @Column(name = "LATITUDE")
  private Long latitude;

  @Column(name = "LONGITUDE")
  private Long longitude;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "DESCRIPTION")
  private String description;
}
