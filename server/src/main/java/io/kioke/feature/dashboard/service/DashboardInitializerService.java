package io.kioke.feature.dashboard.service;

import io.kioke.feature.dashboard.domain.Dashboard;
import io.kioke.feature.dashboard.repository.DashboardRepository;
import io.kioke.feature.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardInitializerService {

  private final DashboardRepository dashboardRepository;

  @Transactional
  public void initializeDashboard(User user) {
    Dashboard dashboard = Dashboard.builder().user(user).build();
    dashboardRepository.save(dashboard);
    log.debug("Initialized dashboard for user {}", user.getUserId());
  }
}
