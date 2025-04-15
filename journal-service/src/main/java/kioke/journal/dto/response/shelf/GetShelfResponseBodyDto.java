package kioke.journal.dto.response.shelf;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kioke.journal.dto.data.journal.JournalPreviewDto;
import kioke.journal.model.Journal;
import kioke.journal.model.Shelf;
import lombok.Builder;

@Builder
public record GetShelfResponseBodyDto(
    String shelfId,
    String name,
    List<JournalPreviewDto> journals,
    @JsonProperty("isArchive") boolean isArchive) {

  public static List<GetShelfResponseBodyDto> from(List<Shelf> shelves, List<String> bookmarks) {
    Map<String, Boolean> bookmarksMap =
        bookmarks.stream().collect(Collectors.toMap(journalId -> journalId, journalId -> true));

    return shelves.stream()
        .map(
            shelf ->
                GetShelfResponseBodyDto.builder()
                    .shelfId(shelf.getId())
                    .name(shelf.getName())
                    .journals(
                        shelf.getShelfSlots().stream()
                            .map(
                                shelfSlot -> {
                                  Journal journal = shelfSlot.getJournal();
                                  return JournalPreviewDto.from(
                                      journal, bookmarksMap.containsKey(journal.getJournalId()));
                                })
                            .toList())
                    .isArchive(shelf.isArchive())
                    .build())
        .toList();
  }
}
