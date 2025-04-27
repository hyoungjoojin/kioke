package kioke.journal.dto.response.journal;

import java.util.List;
import kioke.journal.dto.data.journal.JournalPreviewDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder(access = AccessLevel.PRIVATE)
@Slf4j
public record GetJournalsResponseBodyDto(List<JournalPreviewDto> journals) {

  public static GetJournalsResponseBodyDto from(List<JournalPreviewDto> journalPreviewDtos) {
    log.debug("start mapping List<JournalPreviewDto> to GetJournalsResponseBodyDto");
    GetJournalsResponseBodyDto responseBodyDto = new GetJournalsResponseBodyDto(journalPreviewDtos);
    log.debug("finished mapping List<JournalPreviewDto> to GetJournalsResponseBodyDto");

    return responseBodyDto;
  }
}
