package kioke.journal.dto.data.journal;

import java.time.LocalDateTime;
import java.util.List;
import kioke.journal.dto.data.page.PagePreviewDto;
import kioke.journal.dto.data.user.UserDto;
import kioke.journal.model.Journal;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record JournalDto(
    String journalId,
    String title,
    String description,
    List<UserDto> users,
    List<PagePreviewDto> pages,
    boolean bookmarked,
    LocalDateTime createdAt,
    LocalDateTime lastModified) {

  public static JournalDto from(Journal journal, boolean bookmarked) {
    return JournalDto.builder()
        .journalId(journal.getJournalId())
        .title(journal.getTitle())
        .description(journal.getDescription())
        .users(journal.getUsers().stream().map(role -> UserDto.from(role)).toList())
        .pages(journal.getPages().stream().map(page -> PagePreviewDto.from(page)).toList())
        .bookmarked(bookmarked)
        .createdAt(journal.getCreatedAt())
        .lastModified(journal.getLastModified())
        .build();
  }
}
