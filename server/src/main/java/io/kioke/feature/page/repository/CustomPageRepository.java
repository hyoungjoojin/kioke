package io.kioke.feature.page.repository;

import io.kioke.feature.page.domain.Page;
import java.util.Optional;

interface CustomPageRepository {

  Optional<Page> findWithBlocksById(String pageId);
}
