package io.kioke.feature.dashboard.domain.widget;

import io.kioke.feature.dashboard.constant.WidgetType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "WEATHER_WIDGET_TABLE")
@DiscriminatorValue(WidgetType.Values.WEATHER)
@SuperBuilder
public class WeatherWidget extends Widget {}
