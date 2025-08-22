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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DASHBOARD_TABLE")
public class Dashboard {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "DASHBOARD_ID")
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  @OneToMany(mappedBy = "dashboard", orphanRemoval = true, cascade = CascadeType.MERGE)
  private List<Widget> widgets;

  protected Dashboard() {}

  private Dashboard(User user) {
    this.user = user;
    this.widgets = new ArrayList<>();
  }

  public String getId() {
    return id;
  }

  public List<Widget> getWidgets() {
    return widgets;
  }

  public static Dashboard from(User user) {
    return new Dashboard(user);
  }

  public void setWidgets(List<Widget> widgets) {
    this.widgets.clear();
    this.widgets.addAll(widgets);
  }
}
