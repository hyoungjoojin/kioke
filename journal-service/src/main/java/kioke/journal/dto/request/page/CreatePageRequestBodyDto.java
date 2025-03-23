package kioke.journal.dto.request.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePageRequestBodyDto {
  private String title = "";
}
