package io.kioke.feature.dashboard.domain;

import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DASHBOARD_TABLE")
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dashboard {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "DASHBOARD_ID")
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  @OneToMany(
      mappedBy = "dashboard",
      orphanRemoval = true,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Widget> widgets;
}
