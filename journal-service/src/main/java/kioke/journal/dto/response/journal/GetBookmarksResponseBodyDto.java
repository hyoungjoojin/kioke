package kioke.journal.dto.response.journal;

import java.util.List;
import kioke.journal.dto.data.journal.JournalPreviewDto;
import kioke.journal.model.Bookmark;

public record GetBookmarksResponseBodyDto(List<JournalPreviewDto> journals) {

  public static GetBookmarksResponseBodyDto from(List<Bookmark> bookmarks) {
    List<JournalPreviewDto> journals =
        bookmarks.stream()
            .map(bookmark -> JournalPreviewDto.from(bookmark.getJournal(), true))
            .toList();

    return new GetBookmarksResponseBodyDto(journals);
  }
}
