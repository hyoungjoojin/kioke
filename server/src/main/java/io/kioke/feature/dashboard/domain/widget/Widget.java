package io.kioke.feature.dashboard.domain.widget;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.Dashboard;
import io.kioke.feature.dashboard.domain.widget.content.WidgetContent;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "WIDGET_TABLE")
public class Widget {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String widgetId;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "DASHBOARD_ID", nullable = false)
  private Dashboard dashboard;

  @Column(name = "WIDGET_TYPE", nullable = false)
  @Enumerated(EnumType.STRING)
  private WidgetType type;

  @Column(name = "POSITION_X", nullable = false)
  private int x;

  @Column(name = "POSITION_Y", nullable = false)
  private int y;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "CONTENT")
  private WidgetContent content;

  public void setDashboard(Dashboard dashboard) {
    this.dashboard = dashboard;
  }

  public void setType(WidgetType type) {
    this.type = type;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setContent(WidgetContent content) {
    this.content = content;
  }
}
