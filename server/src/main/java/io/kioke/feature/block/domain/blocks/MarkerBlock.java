package io.kioke.feature.block.domain.blocks;

import io.kioke.feature.block.domain.Block;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "MARKER_BLOCK_TABLE")
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
