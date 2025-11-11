package io.kioke.feature.collection.dto;

import java.util.List;

public record CollectionDto(String id, String name, boolean isDefault, List<Journal> journals) {

  public static record Journal(String id, String title) {}
}
