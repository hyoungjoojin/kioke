package io.kioke.feature.user.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserDto(@NotEmpty String userId) {}
