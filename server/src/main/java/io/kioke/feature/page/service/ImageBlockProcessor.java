package io.kioke.feature.page.service;

import io.kioke.feature.image.domain.Image;
import io.kioke.feature.image.service.ImageService;
import io.kioke.feature.media.service.MediaService;
import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.domain.block.ImageBlock;
import io.kioke.feature.page.domain.block.ImageBlockImage;
import io.kioke.feature.page.dto.BlockContentDto;
import io.kioke.feature.page.dto.BlockDto;
import io.kioke.feature.page.dto.ImageBlockContentDto;
import io.kioke.feature.user.domain.User;
import java.net.URL;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageBlockProcessor implements BlockProcessor {

  private final ImageService imageService;
  private final MediaService mediaService;

  @Override
  public BlockType type() {
    return BlockType.IMAGE_BLOCK;
  }

  @Override
  public BlockDto map(Block block) {
    ImageBlock imageBlock = (ImageBlock) block;

    List<ImageBlockImage> imageBlockImages = imageBlock.getImages();
    Map<String, URL> urls =
        mediaService.getPresignedUrl(
            imageBlockImages.stream().map(imageBlockImage -> imageBlockImage.getImage()).toList());

    List<ImageBlockContentDto.Image> images =
        imageBlockImages.stream()
            .map(
                imageBlockImage ->
                    new ImageBlockContentDto.Image(
                        imageBlockImage.getId(),
                        urls.get(imageBlockImage.getId()).toExternalForm(),
                        imageBlockImage.getDescription(),
                        imageBlockImage.getImage().getWidth(),
                        imageBlockImage.getImage().getHeight()))
            .toList();

    ImageBlockContentDto content = new ImageBlockContentDto(BlockType.IMAGE_BLOCK, images);
    return new BlockDto(block.getBlockId(), content);
  }

  @Override
  public Block createBlock(User requester, BlockContentDto content) {
    ImageBlock imageBlock = new ImageBlock();
    return imageBlock;
  }

  @Override
  public Block updateBlock(User requester, Block block, BlockContentDto content) {
    ImageBlock imageBlock = (ImageBlock) block;

    ImageBlockContentDto imageBlockContent = (ImageBlockContentDto) content;

    List<String> imageIds =
        imageBlockContent.images().stream().map(image -> image.imageId()).toList();
    List<Image> images =
        imageService.getImages(imageIds).stream()
            .filter(
                image ->
                    image.isPending()
                        && image.getUploader().getUserId().equals(requester.getUserId()))
            .map(
                image -> {
                  image.setPending(false);
                  return image;
                })
            .toList();

    List<ImageBlockImage> imageBlockImages =
        images.stream()
            .map(
                image -> {
                  ImageBlockImage imageBlockImage = new ImageBlockImage();
                  imageBlockImage.setImage(image);
                  imageBlockImage.setImageBlock(imageBlock);
                  return imageBlockImage;
                })
            .toList();

    imageBlock.getImages().clear();
    imageBlock.getImages().addAll(imageBlockImages);
    return imageBlock;
  }
}
