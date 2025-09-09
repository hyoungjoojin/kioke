package io.kioke.feature.friend.dto;

import java.time.Instant;

public record FriendDto(String friendUserId, Instant since) {}
