package io.kioke.feature.journal.dto.response;

import java.util.List;

public record GetJournalsResponse(List<Journal> journals, String cursor, boolean hasNext) {

  public static record Journal(String id, String title) {}
}
