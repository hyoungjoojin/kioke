package io.kioke.feature.dashboard.dto;

import java.util.List;

public record DashboardDto(String id, List<WidgetDto> widgets) {}
