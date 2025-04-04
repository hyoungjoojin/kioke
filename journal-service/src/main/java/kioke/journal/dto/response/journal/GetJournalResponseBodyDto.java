package kioke.journal.dto.response.journal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import kioke.journal.constant.Role;
import kioke.journal.model.Journal;
import kioke.journal.model.JournalRole;
import kioke.journal.model.Page;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetJournalResponseBodyDto {
  private String jid;
  private String title;
  private String description;
  private List<UserDto> users;
  private List<PageDto> pages;
  private LocalDateTime createdAt;
  private LocalDateTime lastModified;

  public static GetJournalResponseBodyDto from(Journal journal) {
    return GetJournalResponseBodyDto.builder()
        .jid(journal.getJid())
        .title(journal.getTitle())
        .description(journal.getDescription())
        .users(UserDto.from(journal.getUsers()))
        .pages(PageDto.from(journal.getPages()))
        .createdAt(journal.getCreatedAt())
        .lastModified(journal.getLastModified())
        .build();
  }

  @Data
  @Builder
  private static class UserDto {
    private String userId;
    private Role role;

    public static List<UserDto> from(List<JournalRole> journalRoles) {
      return journalRoles.stream()
          .map(
              journalRole ->
                  UserDto.builder()
                      .userId(journalRole.getUser().getUid())
                      .role(journalRole.getRole())
                      .build())
          .toList();
    }
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
