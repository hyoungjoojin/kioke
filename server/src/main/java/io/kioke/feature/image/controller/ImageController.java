package io.kioke.feature.image.controller;

import io.kioke.feature.media.service.MediaService;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

  private final MediaService mediaService;

  public ImageController(MediaService mediaService) {
    this.mediaService = mediaService;
  }

  @PutMapping(
      path = "/image/upload",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public String uploadImage(@RequestPart(name = "image") MultipartFile image) throws IOException {
    return mediaService.uploadFile(image);
  }

  @GetMapping("/image/{imageId}")
  public String getImage(@PathVariable String imageId) {
    return mediaService.getPresignedUrl(imageId);
  }
}
