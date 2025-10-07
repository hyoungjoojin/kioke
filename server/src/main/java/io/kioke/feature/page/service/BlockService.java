package io.kioke.feature.page.service;

import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.dto.BlockDto;
import io.kioke.feature.page.dto.request.CreateBlockRequest;
import io.kioke.feature.page.dto.request.UpdateBlockRequest;
import io.kioke.feature.page.repository.BlockRepository;
import io.kioke.feature.user.domain.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BlockService {

  private final BlockRepository blockRepository;
  private final Map<BlockType, BlockProcessor> blockProcessors;

  public BlockService(BlockRepository blockRepository, List<BlockProcessor> blockProcessors) {
    this.blockRepository = blockRepository;

    this.blockProcessors = new HashMap<>();
    blockProcessors.stream()
        .forEach(
            blockProcessor -> {
              this.blockProcessors.put(blockProcessor.type(), blockProcessor);
            });
  }

  @Transactional(readOnly = true)
  public List<BlockDto> getBlocksInPage(String pageId) {
    List<BlockDto> blocks =
        blockRepository.findAllByPageId(pageId).stream()
            .map(block -> getBlockProcessor(block.getBlockType()).map(block))
            .toList();

    log.debug("Found {} blocks in page {}", blocks.size(), pageId);
    return blocks;
  }

  @Transactional
  public BlockDto createBlock(String requesterId, CreateBlockRequest request) {
    BlockType type = request.content().type();
    Block block = getBlockProcessor(type).createBlock(User.of(requesterId), request.content());

    Page page = Page.getReferenceById(request.pageId());
    block.setPage(page);

    block = blockRepository.save(block);
    return BlockDto.of(block.getBlockId());
  }

  @Transactional
  public void updateBlock(String requesterId, String blockId, UpdateBlockRequest request) {
    BlockType type = request.content().type();
    Block block = blockRepository.findById(blockId).orElseThrow();
    getBlockProcessor(type).updateBlock(User.of(requesterId), block, request.content());
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
