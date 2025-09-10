package io.kioke.feature.image.repository;

import io.kioke.feature.image.domain.PageImage;
import io.kioke.feature.page.domain.Page;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageImageRepository extends JpaRepository<PageImage, String> {

  public List<PageImage> findByPage(Page page);
}
