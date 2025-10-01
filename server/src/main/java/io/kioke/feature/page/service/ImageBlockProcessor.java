package io.kioke.feature.page.service;

import io.kioke.feature.image.domain.Image;
import io.kioke.feature.image.service.ImageService;
import io.kioke.feature.media.domain.MediaContext;
import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.domain.block.ImageBlock;
import io.kioke.feature.page.domain.block.ImageBlockImage;
import io.kioke.feature.page.dto.BlockContent;
import io.kioke.feature.page.dto.ImageBlockContent;
import io.kioke.feature.user.domain.User;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageBlockProcessor implements BlockProcessor {

  private final ImageService imageService;

  public ImageBlockProcessor(ImageService imageService) {
    this.imageService = imageService;
  }

  @Override
  public BlockType type() {
    return BlockType.IMAGE_BLOCK;
  }

  @Override
  @Transactional
  public ImageBlock createBlock(User requester, BlockContent content) {
    ImageBlock imageBlock = new ImageBlock();

    ImageBlockContent imageBlockContent = (ImageBlockContent) content;

    List<String> imageIds =
        imageBlockContent.images().stream().map(image -> image.imageId()).toList();
    Map<String, Image> images =
        imageService.getPendingImages(requester, imageIds).stream()
            .map(
                image -> {
                  image.setContext(MediaContext.IMAGE_BLOCK);
                  return image;
                })
            .collect(Collectors.toMap(Image::getId, Function.identity()));

    List<ImageBlockImage> imageBlockImages =
        imageBlockContent.images().stream()
            .map(
                image -> {
                  ImageBlockImage imageBlockImage = new ImageBlockImage();
                  imageBlockImage.setImageBlock(imageBlock);
                  imageBlockImage.setImage(images.get(image.imageId()));
                  imageBlockImage.setDescription(image.description());
                  return imageBlockImage;
                })
            .toList();

    imageBlock.setImages(imageBlockImages);
    return imageBlock;
  }

  @Override
  @Transactional
  public Block updateBlock(User requester, Block block, BlockContent content) {
    ImageBlock imageBlock = (ImageBlock) block;

    ImageBlockContent imageBlockContent = (ImageBlockContent) content;
    List<String> imageIds =
        imageBlockContent.images().stream().map(image -> image.imageId()).toList();

    List<Image> images =
        imageService.getPendingImages(requester, imageIds).stream()
            .map(
                image -> {
                  image.setContext(MediaContext.IMAGE_BLOCK);
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

    imageBlock.getImages().addAll(imageBlockImages);
    return imageBlock;
  }
}
