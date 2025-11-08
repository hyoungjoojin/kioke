package io.kioke.feature.block.service.processor;

import io.kioke.feature.block.domain.Block;
import io.kioke.feature.block.domain.BlockType;
import io.kioke.feature.block.domain.blocks.GalleryBlock;
import io.kioke.feature.block.domain.blocks.ImageBlock;
import io.kioke.feature.block.dto.BlockDto;
import io.kioke.feature.block.dto.ImageBlockDto;
import io.kioke.feature.block.dto.operation.UpdateBlockOperation;
import io.kioke.feature.block.repository.BlockRepository;
import io.kioke.feature.image.domain.Image;
import io.kioke.feature.image.service.ImageService;
import io.kioke.feature.media.service.MediaService;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ImageBlockProcessor implements BlockProcessor {

  private final BlockRepository blockRepository;
  private final MediaService mediaService;
  private final ImageService imageService;

  @Override
  public BlockType type() {
    return BlockType.IMAGE_BLOCK;
  }

  @Override
  public BlockDto map(Block block) {
    ImageBlock imageBlock = (ImageBlock) block;
    Image image = imageBlock.getImage();

    GalleryBlock parentBlock = imageBlock.getParent();
    URL url = mediaService.getPresignedUrl(image);

    return new ImageBlockDto(
        block.getBlockId(),
        block.getType(),
        parentBlock.getBlockId(),
        url.toString(),
        imageBlock.getDescription(),
        image.getWidth(),
        image.getHeight());
  }

  @Override
  public Block getUpdatedBlock(UpdateBlockOperation operation) {
    ImageBlock imageBlock = new ImageBlock();
    var content = (UpdateBlockOperation.ImageBlockContent) operation.content();

    GalleryBlock parentBlock =
        (GalleryBlock) blockRepository.findById(content.parentId()).orElseThrow();
    imageBlock.setParent(parentBlock);

    Image image = imageService.getImage(content.imageId());
    imageBlock.setImage(image);

    return imageBlock;
  }
}
