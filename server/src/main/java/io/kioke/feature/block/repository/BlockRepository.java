package io.kioke.feature.block.repository;

import io.kioke.feature.block.domain.Block;
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
      WHERE
        b.page.pageId = :pageId
      """)
  List<Block> findAllByPageId(String pageId);
}
