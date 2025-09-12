package io.kioke.feature.profile.dto.response;

import io.kioke.feature.preferences.constant.Theme;
import java.time.Instant;

public record GetMyProfileResponse(
    String userId, String email, String name, boolean onboarded, Instant createdAt, Theme theme) {}
