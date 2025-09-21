package io.kioke.feature.profile.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.Instant;

@JsonInclude(value = Include.NON_NULL)
public record ProfileResponse(
    String userId, String email, String name, boolean onboarded, Instant createdAt) {}
