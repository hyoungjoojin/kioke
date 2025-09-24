package io.kioke.feature.dashboard.util;

import io.kioke.feature.dashboard.domain.Dashboard;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.dashboard.dto.response.DashboardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DashboardMapper {

  public DashboardResponse toDashboardResponse(Dashboard dashboard);

  @Mapping(source = "widgetId", target = "id")
  DashboardResponse.Widget toDto(Widget widget);

  public DashboardResponse toDto(Dashboard dashboard);
}
