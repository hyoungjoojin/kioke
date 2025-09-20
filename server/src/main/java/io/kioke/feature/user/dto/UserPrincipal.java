package io.kioke.feature.user.dto;

public record UserPrincipal(String userId) {

  public static UserPrincipal of(String userId) {
    return new UserPrincipal(userId);
  }
}
