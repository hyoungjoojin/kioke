package kioke.journal.dto.response.journal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import kioke.journal.dto.data.journal.JournalDto;
import kioke.journal.dto.data.page.PagePreviewDto;
import kioke.journal.dto.data.user.UserDto;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetJournalResponseBodyDto(
    String journalId,
    String title,
    String description,
    List<UserDto> users,
    List<PagePreviewDto> pages,
    @JsonProperty("bookmarked") boolean bookmarked,
    LocalDateTime createdAt,
    LocalDateTime lastModified) {

  public static GetJournalResponseBodyDto from(JournalDto journalDto) {
    return GetJournalResponseBodyDto.builder()
        .journalId(journalDto.journalId())
        .title(journalDto.title())
        .description(journalDto.description())
        .users(journalDto.users())
        .pages(journalDto.pages())
        .bookmarked(journalDto.bookmarked())
        .createdAt(journalDto.createdAt())
        .lastModified(journalDto.lastModified())
        .build();
  }
}
