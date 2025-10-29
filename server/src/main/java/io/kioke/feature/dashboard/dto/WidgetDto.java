package io.kioke.feature.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kioke.feature.dashboard.constant.WidgetType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record WidgetDto(
    @NotNull WidgetType type,
    @NotNull @Min(0) @Max(100) int x,
    @NotNull @Min(0) @Max(100) int y,
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            visible = true,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type")
        @JsonSubTypes({
          @Type(value = JournalCoverWidgetContent.class, name = WidgetType.Values.JOURNAL_COVER),
          @Type(value = WeatherWidgetContent.class, name = WidgetType.Values.WEATHER)
        })
        Content content) {

  public interface Content {}

  public record JournalCoverWidgetContent(String journalId) implements Content {}

  public record WeatherWidgetContent() implements Content {}
}
