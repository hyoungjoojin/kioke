package io.kioke.feature.page.repository;

import io.kioke.feature.journal.dto.projection.JournalPermissionProjection;
import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.domain.block.Block;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, String> {

  @Query(
      """
        SELECT
          b
        FROM
          Block b
          LEFT JOIN FETCH TREAT(b as ImageBlock).images
        WHERE
          b.id in :blockIds
      """)
  public List<Block> fetchBlocks(List<String> blockIds);

  @Query(
      """
        SELECT u.role, j.isPublic
          FROM Page p
            LEFT JOIN Journal j on p.journal.journalId = j.journalId
            LEFT JOIN JournalUser u ON j.journalId = u.journal.journalId
        WHERE
          p.pageId = :pageId AND u.user.userId = :userId
      """)
  public Optional<JournalPermissionProjection> findJournalUserRole(String pageId, String userId);
}
