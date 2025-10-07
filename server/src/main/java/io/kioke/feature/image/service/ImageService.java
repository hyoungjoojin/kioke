package io.kioke.feature.image.service;

import io.kioke.feature.image.domain.Image;
import io.kioke.feature.image.dto.request.UploadImageRequest;
import io.kioke.feature.image.dto.response.UploadImageResponse;
import io.kioke.feature.image.repository.ImageRepository;
import io.kioke.feature.media.dto.response.MediaResponse;
import io.kioke.feature.media.service.MediaService;
import io.kioke.feature.user.domain.User;
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
    Image image = new Image();
    image.setKey("image/" + request.name() + "-" + UUID.randomUUID().toString());
    image.setName(request.name());
    image.setUploader(User.of(uploaderId));
    image.setDimensions(request.width(), request.height());
    image.setPending(true);

    image = imageRepository.save(image);

    MediaResponse mediaResponse = mediaService.uploadMedia(image);
    return new UploadImageResponse(image.getId(), mediaResponse.url());
  }

  @Transactional(readOnly = true)
  public List<Image> getImages(List<String> imageIds) {
    List<Image> images = imageRepository.findAllById(imageIds);
    return images;
  }
}
