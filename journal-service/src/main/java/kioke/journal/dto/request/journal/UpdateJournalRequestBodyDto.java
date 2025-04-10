package kioke.journal.dto.request.journal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateJournalRequestBodyDto {

  private String title;
}
