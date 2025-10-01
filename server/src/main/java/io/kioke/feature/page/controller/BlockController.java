package io.kioke.feature.page.controller;

import io.kioke.common.auth.AuthenticatedUser;
import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.dto.request.CreateBlockRequest;
import io.kioke.feature.page.dto.request.UpdateBlockRequest;
import io.kioke.feature.page.dto.response.CreateBlockResponse;
import io.kioke.feature.page.service.BlockService;
import io.kioke.feature.page.util.PageMapper;
import io.kioke.feature.user.dto.UserPrincipal;
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
  private final PageMapper pageMapper;

  public BlockController(BlockService blockService, PageMapper pageMapper) {
    this.blockService = blockService;
    this.pageMapper = pageMapper;
  }

  @PostMapping("/blocks")
  @ResponseStatus(HttpStatus.CREATED)
  public CreateBlockResponse createBlock(
      @AuthenticatedUser UserPrincipal user, @RequestBody @Validated CreateBlockRequest request) {
    Block block = blockService.createBlock(user.userId(), request);
    return pageMapper.mapToCreateBlockResponse(block);
  }

  @PatchMapping("/blocks/{blockId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateBlock(
      @AuthenticatedUser UserPrincipal user,
      @PathVariable String blockId,
      @RequestBody @Validated UpdateBlockRequest request) {
    blockService.updateBlock(user.userId(), blockId, request);
  }

  @DeleteMapping("/blocks/{blockId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBlock(@PathVariable String blockId) {
    blockService.deleteBlock(blockId);
  }
}
