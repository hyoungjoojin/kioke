package io.kioke.feature.dashboard.repository;

import io.kioke.feature.dashboard.domain.Dashboard;
import io.kioke.feature.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, String> {

  @Query("SELECT d FROM Dashboard d LEFT JOIN FETCH d.widgets WHERE d.user = :user")
  public Optional<Dashboard> findByUser(User user);
}
