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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DASHBOARD_ID", nullable = false)
  private Dashboard dashboard;

  @Column(name = "WIDGET_TYPE", nullable = false)
  @Enumerated(EnumType.STRING)
  private WidgetType type;

  @Column(name = "POSITION_X", nullable = false)
  private int x;

  @Column(name = "POSITION_Y", nullable = false)
  private int y;

  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "CONTENT")
  private WidgetContent content;

  protected Widget() {}

  private Widget(Dashboard dashboard, WidgetType type, int x, int y, WidgetContent content) {
    this.dashboard = dashboard;
    this.type = type;
    this.x = x;
    this.y = y;
    this.content = content;
  }

  public String getWidgetId() {
    return widgetId;
  }

  public Dashboard getDashboard() {
    return dashboard;
  }

  public WidgetType getType() {
    return type;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public WidgetContent getContent() {
    return content;
  }

  public static WidgetBuilder builder() {
    return new WidgetBuilder();
  }

  public static class WidgetBuilder {

    private Dashboard dashboard;
    private WidgetType type;
    private int x;
    private int y;
    private WidgetContent content;

    public WidgetBuilder dashboard(Dashboard dashboard) {
      this.dashboard = dashboard;
      return this;
    }

    public WidgetBuilder type(WidgetType type) {
      this.type = type;
      return this;
    }

    public WidgetBuilder position(int x, int y) {
      this.x = x;
      this.y = y;
      return this;
    }

    public WidgetBuilder content(WidgetContent content) {
      this.content = content;
      return this;
    }

    public Widget build() {
      return new Widget(dashboard, type, x, y, content);
    }
  }
}
