package com.kioke.auth.model;

import com.kioke.auth.constant.Permission;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Table
@Data
public class AclEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private String id;

  @ManyToOne
  @JoinColumn(name = "user", referencedColumnName = "uid")
  private User user;

  @ManyToOne
  @JoinColumn(name = "journal", referencedColumnName = "jid")
  private Journal journal;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PERMISSION", joinColumns = @JoinColumn(name = "id"))
  @Enumerated(EnumType.STRING)
  private List<Permission> permissions;
}
