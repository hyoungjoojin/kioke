package io.kioke.feature.image.controller;

import io.kioke.annotation.AuthenticatedUser;
import io.kioke.exception.auth.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.exception.page.PageNotFoundException;
import io.kioke.feature.image.dto.request.UploadImageRequestDto;
import io.kioke.feature.image.service.ImageService;
import io.kioke.feature.user.dto.UserDto;
import java.io.IOException;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

  private final ImageService imageService;

  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

  @GetMapping("/images/{imageId}")
  public String getImage(
      @AuthenticatedUser UserDto user, @PathVariable String imageId, @RequestParam String pageId)
      throws PageNotFoundException, JournalNotFoundException {
    return imageService.getImage(user, pageId, imageId);
  }

  @GetMapping("/images/page/{pageId}")
  public List<String> getImagesInPage(@AuthenticatedUser UserDto user, @PathVariable String pageId)
      throws PageNotFoundException, JournalNotFoundException {
    return imageService.getImagesInPage(user, pageId);
  }

  @PutMapping(
      path = "/images",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public String uploadImage(
      @AuthenticatedUser UserDto user,
      @RequestPart(name = "image", required = true) MultipartFile image,
      @RequestPart(name = "json", required = true) UploadImageRequestDto requestBody)
      throws IOException, AccessDeniedException, PageNotFoundException {
    return imageService.uploadImage(user, image, requestBody);
  }
}
