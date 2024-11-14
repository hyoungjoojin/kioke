package com.kioke.journal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "page")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page {
  @Id private String id;
}
