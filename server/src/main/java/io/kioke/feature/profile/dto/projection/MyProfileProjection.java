package io.kioke.feature.profile.dto.projection;

import java.time.Instant;

public record MyProfileProjection(
    String userId, String email, String name, boolean onboarded, Instant createdAt) {}
