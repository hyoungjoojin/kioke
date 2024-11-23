package com.kioke.journal.dto.data.page;

import com.kioke.journal.dto.request.page.CreatePageRequestBodyDto;
import java.time.LocalDate;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePageDto {
  private final Optional<LocalDate> date;
  private final Optional<String> template;

  public static CreatePageDto from(CreatePageRequestBodyDto createPageRequestBodyDto) {
    return CreatePageDto.builder()
        .date(Optional.ofNullable(createPageRequestBodyDto.getDate()))
        .template(Optional.ofNullable(createPageRequestBodyDto.getTemplate()))
        .build();
  }
}
