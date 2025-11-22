package io.kioke.feature.journal.dto.request;

public record GetJournalsParams(String query, int size, String cursor) {}
