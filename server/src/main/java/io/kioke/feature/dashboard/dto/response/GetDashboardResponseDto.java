package io.kioke.feature.dashboard.dto.response;

import io.kioke.feature.dashboard.dto.WidgetDto;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record GetDashboardResponseDto(@NotNull List<WidgetDto> widgets) {}
