package io.kioke.feature.profile.dto.response;

import java.time.Instant;

public record GetProfileResponseDto(
    String email, String name, boolean onboarded, Instant createdAt) {}
