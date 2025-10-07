package io.kioke.feature.page.service;

import io.kioke.exception.page.PageNotFoundException;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.media.service.MediaService;
import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.domain.block.ImageBlockImage;
import io.kioke.feature.page.dto.BlockDto;
import io.kioke.feature.page.dto.PageDto;
import io.kioke.feature.page.dto.PageImageDto;
import io.kioke.feature.page.dto.request.CreatePageRequest;
import io.kioke.feature.page.dto.request.UpdatePageRequest;
import io.kioke.feature.page.repository.PageRepository;
import io.kioke.feature.page.util.PageMapper;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageService {

  private final BlockService blockService;
  private final MediaService mediaService;
  private final PageRepository pageRepository;
  private final PageMapper pageMapper;

  @Transactional(readOnly = true)
  @PreAuthorize("hasPermission(#pageId, 'page', 'read')")
  public PageDto getPageById(String pageId) throws PageNotFoundException {
    Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException());
    List<BlockDto> blocks = blockService.getBlocksInPage(pageId);
    return pageMapper.map(page, blocks);
  }

  @Transactional(readOnly = true)
  @PreAuthorize("hasPermission(#pageId, 'page', 'read')")
  public List<PageImageDto> getPageImages(String pageId) {
    List<ImageBlockImage> imageBlockImages = new ArrayList<>();
    blockService.getAllImageBlocks(pageId).stream()
        .forEach(imageBlock -> imageBlockImages.addAll(imageBlock.getImages()));
    log.debug("Found {} images in page {}", imageBlockImages.size(), pageId);

    Map<String, URL> urls =
        mediaService.getPresignedUrl(
            imageBlockImages.stream().map(imageBlockImage -> imageBlockImage.getImage()).toList());

    List<PageImageDto> images =
        imageBlockImages.stream()
            .map(
                imageBlockImage ->
                    new PageImageDto(
                        imageBlockImage.getId(),
                        urls.get(imageBlockImage.getId()).toExternalForm(),
                        imageBlockImage.getDescription(),
                        imageBlockImage.getImage().getWidth(),
                        imageBlockImage.getImage().getHeight()))
            .toList();
    return images;
  }

  @Transactional
  @PreAuthorize("hasPermission(#request.journalId, 'journal', 'update')")
  public PageDto createPage(CreatePageRequest request) {
    Page page =
        Page.builder()
            .journal(Journal.getReferenceById(request.journalId()))
            .title(request.title())
            .date(request.date())
            .build();

    pageRepository.save(page);
    return pageMapper.map(page, Collections.emptyList());
  }

  @Transactional
  @PreAuthorize("hasPermission(#pageId, 'page', 'update')")
  public void updatePage(String pageId, UpdatePageRequest request) throws PageNotFoundException {
    Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException());

    if (request.title() != null) {
      page.setTitle(request.title());
    }

    if (request.date() != null) {
      page.setDate(request.date());
    }

    pageRepository.save(page);
  }
}
