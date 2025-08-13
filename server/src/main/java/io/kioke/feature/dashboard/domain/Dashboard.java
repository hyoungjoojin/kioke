package io.kioke.feature.dashboard.domain;

import io.kioke.feature.dashboard.constant.ViewerType;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.user.domain.User;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

  @Column(name = "VIEWER_TYPE", nullable = false)
  @Enumerated(EnumType.STRING)
  private ViewerType viewerType;

  @OneToMany(mappedBy = "dashboard", orphanRemoval = true)
  private List<Widget> widgets;

  public List<Widget> getWidgets() {
    return widgets;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setViewerType(ViewerType viewerType) {
    this.viewerType = viewerType;
  }

  public void setWidgets(List<Widget> widgets) {
    this.widgets = widgets;
  }
}
