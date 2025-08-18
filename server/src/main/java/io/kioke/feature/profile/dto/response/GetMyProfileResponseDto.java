package io.kioke.feature.profile.dto.response;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record GetMyProfileResponseDto(
    @NotNull String email, @NotNull String name, @NotNull boolean onboarded, Instant createdAt) {}
