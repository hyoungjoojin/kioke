package io.kioke.feature.page.dto;

public record MapMarkerDto(
    String id, Long latitude, Long longitude, String title, String description) {}
