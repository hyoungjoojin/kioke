package io.kioke.feature.image.controller;

import io.kioke.common.auth.AuthenticatedUser;
import io.kioke.feature.image.dto.request.UploadImageRequest;
import io.kioke.feature.image.dto.response.UploadImageResponse;
import io.kioke.feature.image.service.ImageService;
import io.kioke.feature.user.dto.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

  private final ImageService imageService;

  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

  @PostMapping("/images")
  @ResponseStatus(HttpStatus.OK)
  public UploadImageResponse uploadImage(
      @AuthenticatedUser UserPrincipal user, @RequestBody @Validated UploadImageRequest request) {
    return imageService.uploadImage(user.userId(), request);
  }
}
