package io.kioke.feature.image.service;

import io.kioke.feature.image.domain.Image;
import io.kioke.feature.image.dto.request.UploadImageRequest;
import io.kioke.feature.image.dto.response.ImageResponse;
import io.kioke.feature.image.dto.response.UploadImageResponse;
import io.kioke.feature.image.repository.ImageRepository;
import io.kioke.feature.media.dto.request.GetMediaRequest;
import io.kioke.feature.media.dto.response.MediaResponse;
import io.kioke.feature.media.service.MediaService;
import io.kioke.feature.user.domain.User;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageService {

  private final ImageRepository imageRepository;
  private final MediaService mediaService;

  public ImageService(ImageRepository imageRepository, MediaService mediaService) {
    this.imageRepository = imageRepository;
    this.mediaService = mediaService;
  }

  @Transactional(rollbackFor = Exception.class)
  @PreAuthorize("isAuthenticated()")
  public UploadImageResponse uploadImage(String uploaderId, UploadImageRequest request) {
    String key = UUID.randomUUID().toString();
    Image image = Image.create(key);
    image.setUploader(User.of(uploaderId));
    image.setDimensions(request.width(), request.height());
    image = imageRepository.save(image);

    MediaResponse mediaResponse = mediaService.uploadMedia(image);
    return new UploadImageResponse(image.getId(), mediaResponse.url());
  }

  @Transactional(readOnly = true)
  @PreAuthorize("hasPermission(#request, 'read')")
  public List<ImageResponse> getImages(GetMediaRequest request) {
    if (request.ids().size() == 0) {
      return Collections.emptyList();
    }

    List<Image> images = imageRepository.findAllById(request.ids());

    HashMap<String, MediaResponse> mediaMap = new HashMap<>();
    mediaService.getMedia(images).forEach(media -> mediaMap.put(media.mediaId(), media));

    return images.stream()
        .map(
            image -> {
              MediaResponse media = mediaMap.get(image.getId());
              if (media == null) {
                return null;
              }

              return new ImageResponse(
                  image.getId(), media.url(), image.getWidth(), image.getHeight());
            })
        .toList();
  }

  @Transactional(readOnly = true)
  public List<Image> getPendingImages(User requester, List<String> imageIds) {
    return imageRepository.findAllById(imageIds).stream()
        .filter(
            image ->
                image.isPending() && image.getUploader().getUserId().equals(requester.getUserId()))
        .toList();
  }
}
