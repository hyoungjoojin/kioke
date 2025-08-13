package io.kioke.feature.dashboard.repository;

import io.kioke.feature.dashboard.domain.widget.Widget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetRepository extends JpaRepository<Widget, String> {}
