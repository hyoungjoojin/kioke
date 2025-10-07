package io.kioke.feature.page.domain.block;

import io.kioke.feature.page.domain.Page;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "BLOCK_TABLE")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "BLOCK_TYPE", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class Block {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String blockId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PAGE_ID")
  private Page page;

  public abstract BlockType getBlockType();
}
