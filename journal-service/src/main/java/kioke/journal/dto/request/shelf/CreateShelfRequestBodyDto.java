package kioke.journal.dto.request.shelf;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateShelfRequestBodyDto {
  @NotNull private String name;
}
