package kioke.journal.dto.data.page;

import java.time.LocalDateTime;
import kioke.journal.model.Page;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record PagePreviewDto(String pageId, String title, LocalDateTime createdAt) {

  public static PagePreviewDto from(Page page) {
    return PagePreviewDto.builder()
        .pageId(page.getId())
        .title(page.getTitle())
        .createdAt(page.getCreatedAt())
        .build();
  }
}
