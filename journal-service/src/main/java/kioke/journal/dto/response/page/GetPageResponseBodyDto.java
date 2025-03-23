package kioke.journal.dto.response.page;

import java.time.LocalDate;
import kioke.journal.model.Page;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPageResponseBodyDto {
  private String pageId;
  private LocalDate date;
  private String title;
  private String content;

  public static GetPageResponseBodyDto from(Page page) {
    return GetPageResponseBodyDto.builder()
        .pageId(page.getId())
        .date(page.getDate())
        .title(page.getTitle())
        .content(page.getContent())
        .build();
  }
}
