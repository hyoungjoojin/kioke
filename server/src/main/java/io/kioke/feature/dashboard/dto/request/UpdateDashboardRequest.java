package io.kioke.feature.dashboard.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kioke.feature.dashboard.constant.WidgetType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateDashboardRequest(@NotNull List<@Valid Widget> widgets) {

  public static record Widget(
      @NotNull WidgetType type,
      @NotNull @Min(0) @Max(100) int x,
      @NotNull @Min(0) @Max(100) int y,
      @JsonTypeInfo(
              use = JsonTypeInfo.Id.NAME,
              visible = true,
              include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
              property = "type")
          @JsonSubTypes({
            @Type(value = AddPageWidgetContent.class, name = WidgetType.Values.ADD_PAGE),
            @Type(value = JournalCoverWidgetContent.class, name = WidgetType.Values.JOURNAL_COVER),
            @Type(value = WeatherWidgetContent.class, name = WidgetType.Values.WEATHER)
          })
          WidgetContent content) {}

  public static interface WidgetContent {}

  public static record AddPageWidgetContent(String journalId) implements WidgetContent {}

  public static record JournalCoverWidgetContent(String journalId) implements WidgetContent {}

  public static record WeatherWidgetContent() implements WidgetContent {}
}
