package io.kioke.feature.page.repository;

import io.kioke.feature.page.domain.block.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, String> {}
