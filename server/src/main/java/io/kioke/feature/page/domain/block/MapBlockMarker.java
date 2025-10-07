package io.kioke.feature.page.domain.block;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "MAP_BLOCK_MARKER_TABLE")
public class MapBlockMarker {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MAP_BLOCK_ID")
  private MapBlock mapBlock;

  @Column(name = "LATITUDE")
  private Long latitude;

  @Column(name = "LONGITUDE")
  private Long longitude;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "DESCRIPTION")
  private String description;
}
