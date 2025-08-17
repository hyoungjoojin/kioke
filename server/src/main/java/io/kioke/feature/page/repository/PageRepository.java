package io.kioke.feature.page.repository;

import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.dto.PageDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, String> {

  @Query(
      "SELECT p.pageId, p.journal.journalId, p.title, p.content, p.date "
          + "FROM Page p WHERE p.pageId = :pageId")
  public Optional<PageDto> findByPageId(String pageId);
}
