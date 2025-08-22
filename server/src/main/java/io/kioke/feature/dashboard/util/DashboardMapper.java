package io.kioke.feature.dashboard.util;

import io.kioke.feature.dashboard.domain.Dashboard;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.dashboard.dto.DashboardDto;
import io.kioke.feature.dashboard.dto.response.GetDashboardResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DashboardMapper {

  @Mapping(source = "widgetId", target = "id")
  DashboardDto.Widget toDto(Widget widget);

  public DashboardDto toDto(Dashboard dashboard);

  public GetDashboardResponseDto toGetDashboardResponse(DashboardDto dashboard);
}
