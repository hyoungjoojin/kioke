package com.kioke.journal.dto.response.shelf;

import com.kioke.journal.model.Shelf;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateShelfResponseBodyDto {
  private String shelfId;

  public static CreateShelfResponseBodyDto from(Shelf shelf) {
    return CreateShelfResponseBodyDto.builder().shelfId(shelf.getId()).build();
  }
}
