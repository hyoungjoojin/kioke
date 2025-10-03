package io.kioke.feature.location.domain;

import io.kioke.feature.page.domain.block.MapBlock;
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
@Table(name = "LOCATION_TABLE")
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "LATITUDE")
  private Double latitude;

  @Column(name = "LONGITUDE")
  private Double longitude;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MAP_BLOCK_ID", nullable = false)
  private MapBlock mapBlock;
}
