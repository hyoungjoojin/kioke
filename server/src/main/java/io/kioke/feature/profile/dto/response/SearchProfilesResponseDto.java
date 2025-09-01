package io.kioke.feature.profile.dto.response;

import java.util.List;

public record SearchProfilesResponseDto(String query, List<Profile> profiles) {

  public static record Profile(String userId, String email, String name) {}
}
