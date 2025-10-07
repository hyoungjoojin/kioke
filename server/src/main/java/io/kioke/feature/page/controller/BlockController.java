package io.kioke.feature.page.controller;

import io.kioke.common.auth.AuthenticatedUser;
import io.kioke.feature.page.dto.BlockDto;
import io.kioke.feature.page.dto.request.CreateBlockRequest;
import io.kioke.feature.page.dto.request.UpdateBlockRequest;
import io.kioke.feature.page.service.BlockService;
import io.kioke.feature.user.dto.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
public class BlockController {

  private final BlockService blockService;

  @PostMapping("/blocks")
  @ResponseStatus(HttpStatus.CREATED)
  public BlockDto createBlock(
      @AuthenticatedUser UserPrincipal user, @RequestBody @Validated CreateBlockRequest request) {
    return blockService.createBlock(user.userId(), request);
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
