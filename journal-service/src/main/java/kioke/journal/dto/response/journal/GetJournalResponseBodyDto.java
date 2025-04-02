package kioke.journal.dto.response.journal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import kioke.journal.model.Journal;
import kioke.journal.model.Page;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetJournalResponseBodyDto {
  private String jid;
  private String title;
  private String description;
  private List<PageDto> pages;

  @JsonProperty("bookmarked")
  boolean bookmarked;

  private LocalDateTime createdAt;
  private LocalDateTime lastModified;

  public static GetJournalResponseBodyDto from(Journal journal, boolean bookmarked) {
    return GetJournalResponseBodyDto.builder()
        .jid(journal.getJid())
        .title(journal.getTitle())
        .description(journal.getDescription())
        .pages(PageDto.from(journal.getPages()))
        .bookmarked(bookmarked)
        .createdAt(journal.getCreatedAt())
        .lastModified(journal.getLastModified())
        .build();
  }

  @Data
  @Builder
  private static class PageDto {
    private String id;
    private LocalDate date;

    public static List<PageDto> from(List<Page> pages) {
      return pages.stream()
          .map(
              page -> {
                return PageDto.builder().id(page.getId()).date(page.getDate()).build();
              })
          .toList();
    }
  }
}
