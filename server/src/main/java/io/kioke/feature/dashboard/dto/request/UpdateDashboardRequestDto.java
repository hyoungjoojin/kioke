package io.kioke.feature.dashboard.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.widget.content.JournalCollectionWidgetContent;
import io.kioke.feature.dashboard.domain.widget.content.SingleJournalWidgetContent;
import io.kioke.feature.dashboard.domain.widget.content.WidgetContent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateDashboardRequestDto(@NotNull List<@Valid Widget> widgets) {

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
            @Type(value = JournalCollectionWidgetContent.class, name = "JOURNAL_COLLECTION"),
            @Type(value = SingleJournalWidgetContent.class, name = "SINGLE_JOURNAL")
          })
          WidgetContent content) {}
}
