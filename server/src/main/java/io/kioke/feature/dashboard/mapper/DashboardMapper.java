package io.kioke.feature.dashboard.mapper;

import io.kioke.feature.dashboard.dto.DashboardDto;
import io.kioke.feature.dashboard.dto.response.GetDashboardResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DashboardMapper {

  public GetDashboardResponseDto toGetDashboardResponseDto(DashboardDto dashboard);
}
