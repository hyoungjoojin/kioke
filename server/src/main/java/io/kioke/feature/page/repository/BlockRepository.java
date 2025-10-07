package io.kioke.feature.page.repository;

import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.ImageBlock;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, String> {

  @Query(
      """
      SELECT
        b
      FROM
        Block b
        LEFT JOIN FETCH TREAT(b as ImageBlock).images
      WHERE
        b.page.pageId = :pageId
      """)
  List<Block> findAllByPageId(String pageId);

  @Query(
      """
      SELECT
        b
      FROM
        ImageBlock b
        LEFT JOIN FETCH b.images
      WHERE
        b.page.pageId = :pageId
      """)
  List<ImageBlock> findAllImageBlocksByPageId(String pageId);
}
