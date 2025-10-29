package io.kioke.feature.dashboard.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateDashboardRequestDto(@NotNull List<@Valid WidgetDto> widgets) {}
