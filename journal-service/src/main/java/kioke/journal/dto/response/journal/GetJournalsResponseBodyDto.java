package kioke.journal.dto.response.journal;

import java.util.List;
import kioke.journal.dto.data.journal.JournalPreviewDto;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetJournalsResponseBodyDto(List<JournalPreviewDto> journals) {

  public static GetJournalsResponseBodyDto from(List<JournalPreviewDto> journalPreviewDtos) {
    return GetJournalsResponseBodyDto.builder().journals(journalPreviewDtos).build();
  }
}
