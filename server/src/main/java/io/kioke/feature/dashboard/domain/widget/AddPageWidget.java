package io.kioke.feature.dashboard.domain.widget;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.journal.domain.Journal;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "create_journal_widget_table")
@DiscriminatorValue(WidgetType.Values.ADD_PAGE)
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class AddPageWidget extends Widget {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "JOURNAL_ID", nullable = false)
  private Journal journal;
}
