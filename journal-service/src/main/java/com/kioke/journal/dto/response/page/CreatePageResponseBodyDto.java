package com.kioke.journal.dto.response.page;

import com.kioke.journal.model.Page;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePageResponseBodyDto {
  private String pageId;

  public static CreatePageResponseBodyDto from(Page page) {
    return CreatePageResponseBodyDto.builder().pageId(page.getId()).build();
  }
}
