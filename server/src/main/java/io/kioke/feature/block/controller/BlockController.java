package io.kioke.feature.block.controller;

import io.kioke.feature.block.dto.SaveBlockOperationsResponse;
import io.kioke.feature.block.dto.SaveBlockOperationsResponse.Conversion;
import io.kioke.feature.block.dto.operation.BlockOperation;
import io.kioke.feature.block.service.BlockService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BlockController {

  private final BlockService blockService;

  @PatchMapping("/blocks")
  public SaveBlockOperationsResponse saveBlockOperations(
      @RequestBody @Validated List<BlockOperation> operations) {
    Map<String, String> map = blockService.process(operations);

    List<SaveBlockOperationsResponse.Conversion> conversions =
        map.entrySet().stream()
            .map(entry -> new Conversion(entry.getKey(), entry.getValue()))
            .toList();
    return new SaveBlockOperationsResponse(conversions);
  }
}
