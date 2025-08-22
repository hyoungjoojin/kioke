package io.kioke.feature.collection.dto;

import java.util.List;

public record CollectionDto(String id, String name, List<Journal> journals) {

  public static record Journal(String id, String title) {}
}
