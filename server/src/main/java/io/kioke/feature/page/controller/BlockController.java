package io.kioke.feature.page.controller;

import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.dto.request.CreateBlockRequest;
import io.kioke.feature.page.dto.request.UpdateBlockRequest;
import io.kioke.feature.page.dto.response.CreateBlockResponse;
import io.kioke.feature.page.service.BlockService;
import io.kioke.feature.page.util.PageMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockController {

  private final BlockService blockService;

  private final PageMapper pageMapper;

  public BlockController(BlockService blockService, PageMapper pageMapper) {
    this.blockService = blockService;
    this.pageMapper = pageMapper;
  }

  @PostMapping("/blocks")
  public CreateBlockResponse createBlock(@RequestBody @Validated CreateBlockRequest request) {
    Block block = blockService.createBlock(request);
    return pageMapper.mapToCreateBlockResponse(block);
  }

  @PatchMapping("/blocks/{blockId}")
  public void updateBlock(
      @PathVariable String blockId, @RequestBody @Validated UpdateBlockRequest request) {
    blockService.updateBlock(blockId, request);
  }

  @DeleteMapping("/blocks/{blockId}")
  public void deleteBlock(
      @PathVariable String blockId, @RequestBody @Validated UpdateBlockRequest request) {
    blockService.updateBlock(blockId, request);
  }
}
