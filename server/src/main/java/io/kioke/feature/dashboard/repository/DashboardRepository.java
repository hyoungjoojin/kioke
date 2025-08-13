package io.kioke.feature.dashboard.repository;

import io.kioke.feature.dashboard.constant.ViewerType;
import io.kioke.feature.dashboard.domain.Dashboard;
import io.kioke.feature.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository
    extends JpaRepository<Dashboard, String>, CustomizedDashboardRepository {

  public Optional<Dashboard> findByUserAndViewerType(User user, ViewerType viewerType);
}
