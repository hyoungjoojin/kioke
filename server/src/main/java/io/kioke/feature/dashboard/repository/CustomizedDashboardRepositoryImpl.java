package io.kioke.feature.dashboard.repository;

import io.kioke.feature.dashboard.constant.ViewerType;
import io.kioke.feature.dashboard.dto.DashboardDto;
import io.kioke.feature.dashboard.dto.WidgetDto;
import io.kioke.feature.user.dto.UserDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
class CustomizedDashboardRepositoryImpl implements CustomizedDashboardRepository {

  @PersistenceContext private EntityManager em;

  @Override
  public DashboardDto getDashboard(UserDto user, ViewerType viewerType) {
    String dashboardId =
        em.createQuery(
                "SELECT d.id FROM Dashboard d "
                    + "WHERE d.user.userId = :userId AND d.viewerType = :viewerType",
                String.class)
            .setParameter("userId", user.userId())
            .setParameter("viewerType", viewerType)
            .getSingleResult();

    List<WidgetDto> widgets =
        em.createQuery(
                "SELECT new io.kioke.feature.dashboard.dto.WidgetDto"
                    + "(w.widgetId, w.type, w.x, w.y, w.content) "
                    + "FROM Widget w JOIN w.content "
                    + "WHERE w.dashboard.id = :dashboardId",
                WidgetDto.class)
            .setParameter("dashboardId", dashboardId)
            .getResultList();

    return new DashboardDto(dashboardId, widgets);
  }
}
