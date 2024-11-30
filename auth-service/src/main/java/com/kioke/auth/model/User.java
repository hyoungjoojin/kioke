package com.kioke.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_TABLE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
  @Id
  @Column(name = "uid")
  private String uid;
}
