package com.kioke.journal.model;

import jakarta.validation.constraints.NotNull;
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

@Document(collection = "journals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Journal {
  @Id private String id;

  @NotNull private String title;

  @NotNull private String template;

  private List<Page> pages;

  @CreatedDate private LocalDateTime createdAt;

  @LastModifiedDate private LocalDateTime lastUpdated;
}
