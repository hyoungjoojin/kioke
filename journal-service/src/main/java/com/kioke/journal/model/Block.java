package com.kioke.journal.model;

import com.kioke.journal.constant.BlockType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Block {
  @Id private String id;

  @NotNull private BlockType type;

  private String content;

  @CreatedDate private LocalDateTime createdAt;

  @LastModifiedDate private LocalDateTime lastUpdated;
}
