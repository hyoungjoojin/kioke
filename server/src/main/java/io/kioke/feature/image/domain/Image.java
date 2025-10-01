package io.kioke.feature.image.domain;

import io.kioke.feature.media.domain.Media;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "IMAGE_TABLE")
public class Image extends Media {

  @Column(name = "WIDTH")
  private Long width;

  @Column(name = "HEIGHT")
  private Long height;

  public static Image create(String key) {
    Image image = new Image();
    image.setKey(key);
    image.setContext(null);
    return image;
  }

  public void setDimensions(Long width, Long height) {
    this.width = width;
    this.height = height;
  }

  public Long getWidth() {
    return width;
  }

  public Long getHeight() {
    return height;
  }
}
