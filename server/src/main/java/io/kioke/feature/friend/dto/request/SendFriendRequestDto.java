package io.kioke.feature.friend.dto.request;

import jakarta.validation.constraints.NotNull;

public record SendFriendRequestDto(@NotNull String userId) {}
