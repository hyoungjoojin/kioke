package io.kioke.feature.friend.dto;

import io.kioke.feature.friend.constant.FriendshipStatus;

public record FriendRequestDto(
    String requestId, String requesterId, String requesteeId, FriendshipStatus status) {}
