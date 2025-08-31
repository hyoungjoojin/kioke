package io.kioke.feature.notification.dto.response;

import io.kioke.feature.notification.dto.NotificationDto;
import java.util.List;

public record GetNotificationsResponseDto(List<NotificationDto> notifications) {}
