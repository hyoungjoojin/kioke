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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACL_ENTRY_TABLE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AclEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "uid")
  private User user;

  @ManyToOne
  @JoinColumn(name = "jid")
  private Journal journal;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PERMISSION", joinColumns = @JoinColumn(name = "id"))
  @Enumerated(EnumType.STRING)
  private List<Permission> permissions;
}
