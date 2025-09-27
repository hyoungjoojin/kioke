package io.kioke.feature.page.service;

import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.TextBlock;
import io.kioke.feature.page.dto.request.CreateBlockRequest;
import io.kioke.feature.page.dto.request.UpdateBlockRequest;
import io.kioke.feature.page.repository.BlockRepository;
import io.kioke.feature.page.repository.PageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlockService {

  private static final Logger logger = LoggerFactory.getLogger(BlockService.class);

  private final BlockRepository blockRepository;
  private final PageRepository pageRepository;

  public BlockService(BlockRepository blockRepository, PageRepository pageRepository) {
    this.blockRepository = blockRepository;
    this.pageRepository = pageRepository;
  }

  @Transactional
  public Block createBlock(CreateBlockRequest request) {
    Block block;
    if (request.content() instanceof CreateBlockRequest.TextBlock content) {
      block = new TextBlock();
      ((TextBlock) block).setText(content.text());
    } else {
      logger.debug(
          "Request for createBlock failed due to unknown content type {}", request.content());
      throw new IllegalArgumentException();
    }

    Page page = pageRepository.getReferenceById(request.pageId());
    block.setPage(page);

    block = blockRepository.save(block);
    return block;
  }

  @Transactional
  public Block updateBlock(String blockId, UpdateBlockRequest request) {
    Block block = blockRepository.findById(blockId).orElseThrow();

    if (request.content() instanceof UpdateBlockRequest.TextBlock content) {
      ((TextBlock) block).setText(content.text());
    } else {
      logger.debug(
          "Request for updateBlock failed due to unknown content type {}", request.content());
      throw new IllegalArgumentException();
    }

    block = blockRepository.save(block);
    return block;
  }

  @Transactional
  public void deleteBlock(String blockId) {
    blockRepository.deleteById(blockId);
  }
}
