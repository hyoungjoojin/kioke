package io.kioke.feature.block.service;

import io.kioke.feature.block.domain.Block;
import io.kioke.feature.block.domain.BlockType;
import io.kioke.feature.block.dto.BlockDto;
import io.kioke.feature.block.dto.operation.BlockOperation;
import io.kioke.feature.block.dto.operation.DeleteBlockOperation;
import io.kioke.feature.block.dto.operation.UpdateBlockOperation;
import io.kioke.feature.block.repository.BlockRepository;
import io.kioke.feature.block.service.processor.BlockProcessor;
import io.kioke.feature.page.domain.Page;
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

  @Transactional
  public Map<String, String> process(List<BlockOperation> operations) {
    Map<String, String> ids = new HashMap<>();
    operations.forEach(
        op -> {
          if (op instanceof UpdateBlockOperation updateOp) {
            Block block = updateBlock(updateOp);
            ids.put(updateOp.blockId(), block.getBlockId());

          } else if (op instanceof DeleteBlockOperation deleteOp) {
            deleteBlock(deleteOp);
          }
        });

    return ids;
  }

  @Transactional(readOnly = true)
  public List<BlockDto> getBlocksInPage(Page page) {
    return blockRepository.findAllByPageId(page.getPageId()).stream()
        .map(block -> getBlockProcessor(block.getType()).map(block))
        .toList();
  }

  @Transactional(readOnly = true)
  public List<BlockDto> getBlocksInPage(Page page, BlockType type) {
    return blockRepository.findAllByPageId(page.getPageId(), type).stream()
        .map(block -> getBlockProcessor(block.getType()).map(block))
        .toList();
  }

  @Transactional
  public Block updateBlock(UpdateBlockOperation op) {
    BlockProcessor processor = getBlockProcessor(op.type());
    Block updatedBlock = processor.getUpdatedBlock(op);

    updatedBlock.setPage(Page.of(op.pageId()));
    if (blockRepository.findById(op.blockId()).isPresent()) {
      log.debug("Existing block found with id {}", op.blockId());
      updatedBlock.setBlockId(op.blockId());
    } else {
      log.debug("No existing block found with id {}, creating new block", op.blockId());
    }

    return blockRepository.save(updatedBlock);
  }

  @Transactional
  private void deleteBlock(DeleteBlockOperation op) {
    blockRepository.deleteById(op.blockId());
  }

  private BlockProcessor getBlockProcessor(BlockType blockType) {
    BlockProcessor processor = blockProcessors.get(blockType);
    if (processor == null) {
      throw new IllegalArgumentException(
          "Could not get block processor for block type " + blockType);
    }

    return processor;
  }
}
