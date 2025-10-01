package io.kioke.feature.page.service;

import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.dto.request.CreateBlockRequest;
import io.kioke.feature.page.dto.request.UpdateBlockRequest;
import io.kioke.feature.page.repository.BlockRepository;
import io.kioke.feature.page.repository.PageRepository;
import io.kioke.feature.user.domain.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlockService {

  private final PageRepository pageRepository;
  private final BlockRepository blockRepository;
  private final Map<BlockType, BlockProcessor> blockProcessors;

  public BlockService(
      PageRepository pageRepository,
      BlockRepository blockRepository,
      List<BlockProcessor> blockProcessors) {
    this.pageRepository = pageRepository;
    this.blockRepository = blockRepository;

    this.blockProcessors = new HashMap<>();
    blockProcessors.stream()
        .forEach(
            blockProcessor -> {
              this.blockProcessors.put(blockProcessor.type(), blockProcessor);
            });
  }

  @Transactional
  public Block createBlock(String requesterId, CreateBlockRequest request) {
    Page page = pageRepository.getReferenceById(request.pageId());

    BlockType blockType = request.content().type();

    var processor = blockProcessors.get(blockType);
    if (processor == null) {
      throw new IllegalArgumentException();
    }

    Block block = processor.createBlock(User.of(requesterId), request.content());
    block.setPage(page);

    block = blockRepository.save(block);
    return block;
  }

  @Transactional
  public void updateBlock(String requesterId, String blockId, UpdateBlockRequest request) {
    BlockType blockType = request.content().type();
    var processor = getBlockProcessor(blockType);

    Block block = blockRepository.findById(blockId).orElseThrow();
    processor.updateBlock(User.of(requesterId), block, request.content());
  }

  @Transactional
  public void deleteBlock(String blockId) {
    blockRepository.deleteById(blockId);
  }

  private BlockProcessor getBlockProcessor(BlockType blockType) {
    var processor = blockProcessors.get(blockType);
    if (processor == null) {
      throw new IllegalArgumentException();
    }

    return processor;
  }
}
