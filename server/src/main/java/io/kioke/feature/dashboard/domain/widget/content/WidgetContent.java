package io.kioke.feature.dashboard.domain.widget.content;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class WidgetContent {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
}
