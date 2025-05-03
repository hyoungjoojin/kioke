package kioke.journal.dto.response.journal;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import kioke.journal.dto.data.journal.JournalPreviewDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder(access = AccessLevel.PRIVATE)
@Slf4j
public record GetJournalsResponseBodyDto(@NotNull List<JournalPreviewDto> journals) {

  public static GetJournalsResponseBodyDto from(List<JournalPreviewDto> journalPreviewDtos) {
    GetJournalsResponseBodyDto responseBodyDto = new GetJournalsResponseBodyDto(journalPreviewDtos);

    return responseBodyDto;
  }
}
