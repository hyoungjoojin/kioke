package io.kioke.feature.media.repository;

import io.kioke.feature.media.domain.Media;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, String> {

  @Query("SELECT m FROM Media m WHERE m.id IN :ids")
  List<Media> findByIds(List<String> ids);
}
