package kioke.journal.dto.response.shelf;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kioke.journal.dto.data.journal.JournalPreviewDto;
import kioke.journal.model.Journal;
import kioke.journal.model.Shelf;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetShelvesResponseBodyDto {
  private List<ShelfDto> shelves;

  public static GetShelvesResponseBodyDto from(List<Shelf> shelves, List<Journal> bookmarks) {
    Map<String, Boolean> bookmarksMap =
        bookmarks.stream().collect(Collectors.toMap(Journal::getJid, journal -> true));

    return GetShelvesResponseBodyDto.builder()
        .shelves(shelves.stream().map(shelf -> ShelfDto.from(shelf, bookmarksMap)).toList())
        .build();
  }

  @Data
  @Builder
  private static class ShelfDto {
    private String id;
    private String name;
    private List<JournalPreviewDto> journals;

    @JsonProperty("isArchive")
    private boolean isArchive;

    public static ShelfDto from(Shelf shelf, Map<String, Boolean> bookmarksMap) {
      return ShelfDto.builder()
          .id(shelf.getId())
          .name(shelf.getName())
          .journals(
              shelf.getShelfSlots().stream()
                  .map(
                      shelfSlot -> {
                        Journal journal = shelfSlot.getJournal();
                        return JournalPreviewDto.from(
                            journal, bookmarksMap.containsKey(journal.getJid()));
                      })
                  .toList())
          .isArchive(shelf.isArchive())
          .build();
    }
  }
}
