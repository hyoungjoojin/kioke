package kioke.journal.service;

import java.time.LocalDate;
import kioke.commons.exception.security.AccessDeniedException;
import kioke.journal.constant.Permission;
import kioke.journal.exception.journal.JournalNotFoundException;
import kioke.journal.model.Journal;
import kioke.journal.model.Page;
import kioke.journal.repository.JournalRepository;
import kioke.journal.repository.PageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PageService {

  private final UserJournalMetadataService userJournalMetadataService;

  private final JournalRepository journalRepository;
  private final PageRepository pageRepository;

  public PageService(
      UserJournalMetadataService userJournalMetadataService,
      JournalRepository journalRepository,
      PageRepository pageRepository) {
    this.userJournalMetadataService = userJournalMetadataService;
    this.journalRepository = journalRepository;
    this.pageRepository = pageRepository;
  }

  @Transactional(readOnly = true)
  public Page getPageById(String userId, String journalId, String pageId)
      throws JournalNotFoundException {
    if (!userJournalMetadataService.hasPermission(userId, journalId, Permission.READ)) {
      log.debug("User has no permission to read the journal.");
      throw new JournalNotFoundException(journalId);
    }

    Page page = pageRepository.findById(pageId).get();
    if (!page.getJournal().getJournalId().equals(journalId)) {
      throw new JournalNotFoundException(journalId);
    }

    return page;
  }

  @Transactional
  public Page createPage(String userId, String journalId, String title)
      throws JournalNotFoundException, AccessDeniedException {
    Journal journal =
        journalRepository
            .findById(journalId)
            .orElseThrow(() -> new JournalNotFoundException(journalId));

    if (!userJournalMetadataService.hasPermission(userId, journalId, Permission.WRITE)) {
      log.debug("User has no permission to write to the journal.");
      throw new AccessDeniedException();
    }

    Page page =
        Page.builder().journal(journal).date(LocalDate.now()).title(title).content("").build();

    page = pageRepository.save(page);
    return page;
  }

  @Transactional
  public void updatePage(
      String userId, String journalId, String pageId, String title, String contents)
      throws JournalNotFoundException, AccessDeniedException {
    if (!userJournalMetadataService.hasPermission(userId, journalId, Permission.WRITE)) {
      log.debug("User has no permission to write to the journal.");
      throw new AccessDeniedException();
    }

    Page page =
        pageRepository.findById(pageId).orElseThrow(() -> new JournalNotFoundException(journalId));

    if (title != null) {
      page.setTitle(title);
    }

    if (contents != null) {
      page.setContent(contents);
    }
  }
}
