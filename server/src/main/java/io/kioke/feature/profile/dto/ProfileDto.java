package io.kioke.feature.profile.dto;

import java.time.Instant;

public record ProfileDto(
    String userId,
    String email,
    String name,
    boolean onboarded,
    Instant createdAt,
    Instant lastModifiedAt) {}
