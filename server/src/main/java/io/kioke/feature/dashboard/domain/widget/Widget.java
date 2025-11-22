package io.kioke.feature.dashboard.domain.widget;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.Dashboard;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "WIDGET_TABLE")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "WIDGET_TYPE", discriminatorType = DiscriminatorType.STRING)
@Data
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Widget {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "WIDGET_ID")
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DASHBOARD_ID", nullable = false)
  private Dashboard dashboard;

  @Column(name = "WIDGET_TYPE", nullable = false, insertable = false, updatable = false)
  @Enumerated(EnumType.STRING)
  private WidgetType type;

  @Column(name = "POSITION_X", nullable = false)
  private int x;

  @Column(name = "POSITION_Y", nullable = false)
  private int y;
}
