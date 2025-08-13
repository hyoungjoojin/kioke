package io.kioke.feature.dashboard.repository;

import io.kioke.feature.dashboard.constant.ViewerType;
import io.kioke.feature.dashboard.dto.DashboardDto;
import io.kioke.feature.user.dto.UserDto;

interface CustomizedDashboardRepository {

  public DashboardDto getDashboard(UserDto user, ViewerType viewerType);
}
