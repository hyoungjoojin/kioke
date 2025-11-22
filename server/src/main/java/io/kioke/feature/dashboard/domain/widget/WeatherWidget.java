package io.kioke.feature.dashboard.domain.widget;

import io.kioke.feature.dashboard.constant.WidgetType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "WEATHER_WIDGET_TABLE")
@DiscriminatorValue(WidgetType.Values.WEATHER)
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherWidget extends Widget {}
