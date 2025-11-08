package io.kioke.feature.block.dto;

import java.util.List;

public record SaveBlockOperationsResponse(List<Conversion> conversions) {

  public record Conversion(String before, String after) {}
}
