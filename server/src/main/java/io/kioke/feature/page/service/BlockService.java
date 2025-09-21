package io.kioke.feature.page.service;

import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.ImageBlock;
import io.kioke.feature.page.domain.block.TextBlock;
import io.kioke.feature.page.dto.request.CreateBlockRequest;
import io.kioke.feature.page.dto.request.UpdateBlockRequest;
import io.kioke.feature.page.repository.BlockRepository;
import io.kioke.feature.page.repository.PageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
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
  @PreAuthorize("hasPermission(#request.pageId(), 'page', 'EDIT')")
  public Block createBlock(CreateBlockRequest request) {
    Block block;
    CreateBlockRequest.BlockContent content = request.content();

    if (content instanceof CreateBlockRequest.TextBlock textBlockRequest) {
      block = createTextBlock(textBlockRequest);
    } else if (content instanceof CreateBlockRequest.ImageBlock imageBlockRequest) {
      block = createImageBlock(imageBlockRequest);
    } else {
      throw new IllegalStateException("Unknown block type " + content.getClass());
    }

    block.setPage(pageRepository.getReferenceById(request.pageId()));
    block = blockRepository.save(block);
    return block;
  }

  private TextBlock createTextBlock(CreateBlockRequest.TextBlock request) {
    TextBlock block = new TextBlock();
    block.setText(request.text());
    return block;
  }

  private ImageBlock createImageBlock(CreateBlockRequest.ImageBlock request) {
    ImageBlock block = new ImageBlock();
    return block;
  }

  @Transactional
  @PreAuthorize("hasPermission(#blockId, 'block', 'EDIT')")
  public void updateBlock(String blockId, UpdateBlockRequest request) {}

  @Transactional
  @PreAuthorize("hasPermission(#blockId, 'block', 'DELETE')")
  public void deleteBlock(String blockId) {
    blockRepository.deleteById(blockId);
  }
}
