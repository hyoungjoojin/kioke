package kioke.journal.dto.response.journal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import kioke.journal.constant.Role;
import kioke.journal.dto.data.page.PagePreviewDto;
import kioke.journal.model.Journal;
import kioke.journal.model.JournalRole;
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

  public static GetJournalResponseBodyDto from(Journal journal, boolean isJournalBookmarked) {
    return GetJournalResponseBodyDto.builder()
        .journalId(journal.getJournalId())
        .title(journal.getTitle())
        .description(journal.getDescription())
        .users(journal.getUsers().stream().map(role -> UserDto.from(role)).toList())
        .pages(journal.getPages().stream().map(page -> PagePreviewDto.from(page)).toList())
        .bookmarked(isJournalBookmarked)
        .createdAt(journal.getCreatedAt())
        .lastModified(journal.getLastModified())
        .build();
  }

  private static record UserDto(String userId, Role role) {

    public static UserDto from(JournalRole journalRole) {
      return new UserDto(journalRole.getUser().getUserId(), journalRole.getRole());
    }
  }
}
