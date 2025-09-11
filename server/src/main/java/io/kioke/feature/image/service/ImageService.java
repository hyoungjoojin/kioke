package io.kioke.feature.image.service;

import io.kioke.exception.auth.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.exception.page.PageNotFoundException;
import io.kioke.feature.image.domain.PageImage;
import io.kioke.feature.image.dto.request.UploadImageRequestDto;
import io.kioke.feature.image.repository.PageImageRepository;
import io.kioke.feature.media.service.MediaService;
import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.dto.request.UpdatePageRequestDto;
import io.kioke.feature.page.service.PageService;
import io.kioke.feature.user.dto.UserDto;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

  private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

  private final PageService pageService;
  private final MediaService mediaService;
  private final PageImageRepository pageImageRepository;

  public ImageService(
      PageService pageService, MediaService mediaService, PageImageRepository pageImageRepository) {
    this.pageService = pageService;
    this.mediaService = mediaService;
    this.pageImageRepository = pageImageRepository;
  }

  @Transactional
  public String getImage(UserDto user, String pageId, String imageId)
      throws JournalNotFoundException, PageNotFoundException {
    pageService.getPage(user, pageId);
    return mediaService.getPresignedUrl(imageId);
  }

  @Transactional(readOnly = true)
  public List<String> getImagesInPage(UserDto user, String pageId)
      throws JournalNotFoundException, PageNotFoundException {
    pageService.getPage(user, pageId);

    List<String> imageKeys =
        pageImageRepository.findByPage(Page.createReference(pageId)).stream()
            .map(pageImage -> pageImage.key())
            .toList();
    logger.debug("Found {} images in page {}", imageKeys.size(), pageId);

    List<String> urls = mediaService.batchGetPresignedUrls(imageKeys);
    logger.debug("Generated {} presigned URLs for images in page {}", urls.size(), pageId);

    return urls;
  }

  @Transactional(rollbackFor = Exception.class)
  public String uploadImage(UserDto user, MultipartFile image, UploadImageRequestDto request)
      throws IOException, AccessDeniedException, PageNotFoundException {
    Page page = pageService.updatePage(user, request.pageId(), UpdatePageRequestDto.emptyRequest());

    PageImage pageImage = PageImage.of(page);
    pageImageRepository.save(pageImage);

    mediaService.uploadMedia(pageImage, image);
    return pageImage.id();
  }
}
