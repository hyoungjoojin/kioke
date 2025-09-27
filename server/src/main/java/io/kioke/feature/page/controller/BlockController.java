package io.kioke.feature.page.controller;

import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.dto.request.CreateBlockRequest;
import io.kioke.feature.page.dto.request.UpdateBlockRequest;
import io.kioke.feature.page.dto.response.CreateBlockResponse;
import io.kioke.feature.page.service.BlockService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockController {

  private final BlockService blockService;

  public BlockController(BlockService blockService) {
    this.blockService = blockService;
  }

  @PostMapping("/blocks")
  @ResponseStatus(HttpStatus.CREATED)
  public CreateBlockResponse createBlock(@RequestBody @Validated CreateBlockRequest request) {
    Block block = blockService.createBlock(request);
    return new CreateBlockResponse(block.getBlockId());
  }

  @PatchMapping("/blocks/{blockId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateBlock(
      @PathVariable String blockId, @RequestBody @Validated UpdateBlockRequest request) {
    blockService.updateBlock(blockId, request);
  }

  @DeleteMapping("/blocks/{blockId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBlock(@PathVariable String blockId) {
    blockService.deleteBlock(blockId);
  }
}
