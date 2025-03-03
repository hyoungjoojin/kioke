package com.kioke.journal.dto.response.shelf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kioke.journal.model.Journal;
import com.kioke.journal.model.Shelf;
import java.util.List;
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

  public static GetShelvesResponseBodyDto from(List<Shelf> shelves) {
    return GetShelvesResponseBodyDto.builder()
        .shelves(shelves.stream().map(shelf -> ShelfDto.from(shelf)).toList())
        .build();
  }

  @Data
  @Builder
  private static class ShelfDto {
    private String id;
    private String name;
    private List<JournalDto> journals;

    @JsonProperty("isArchive")
    private boolean isArchive;

    public static ShelfDto from(Shelf shelf) {
      return ShelfDto.builder()
          .id(shelf.getId())
          .name(shelf.getName())
          .journals(shelf.getJournals().stream().map(journal -> JournalDto.from(journal)).toList())
          .isArchive(shelf.isArchive())
          .build();
    }
  }

  @Data
  @Builder
  private static class JournalDto {
    private String id;
    private String title;

    public static JournalDto from(Journal journal) {
      return JournalDto.builder().id(journal.getJid()).title(journal.getTitle()).build();
    }
  }
}
