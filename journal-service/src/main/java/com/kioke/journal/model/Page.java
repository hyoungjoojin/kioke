package com.kioke.journal.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page {
  @Id private String id;

  @NotNull private String template;

  @NotNull private LocalDate date;

  private List<Block> blocks;

  @CreatedDate private LocalDateTime createdAt;

  @LastModifiedDate private LocalDateTime lastUpdated;
}
