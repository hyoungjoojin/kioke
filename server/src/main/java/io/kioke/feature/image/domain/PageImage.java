package io.kioke.feature.image.domain;

import io.kioke.feature.media.domain.Media;
import io.kioke.feature.page.domain.Page;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "PAGE_IMAGE_TABLE")
public class PageImage extends Media {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PAGE_ID", nullable = false)
  private Page page;

  protected PageImage() {
    super(UUID.randomUUID().toString());
  }

  public static PageImage of(Page page) {
    PageImage image = new PageImage();
    image.page = page;
    return image;
  }
}
