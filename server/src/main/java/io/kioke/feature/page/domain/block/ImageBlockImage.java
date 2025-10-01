package io.kioke.feature.page.domain.block;

import io.kioke.feature.image.domain.Image;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "IMAGE_BLOCK_IMAGE_TABLE")
public class ImageBlockImage {

  @Id
  @Column(name = "IMAGE_ID")
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "IMAGE_BLOCK_ID", nullable = false)
  private ImageBlock imageBlock;

  @OneToOne
  @MapsId("IMAGE_ID")
  @JoinColumn(name = "IMAGE_ID")
  private Image image;

  @Column(name = "DESCRIPTION")
  private String description;

  public String getId() {
    return id;
  }

  public void setImageBlock(ImageBlock imageBlock) {
    this.imageBlock = imageBlock;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.id = image.getId();
    this.image = image;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
