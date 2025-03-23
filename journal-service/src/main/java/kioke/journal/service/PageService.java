package kioke.journal.service;

import java.time.LocalDate;
import kioke.journal.model.Journal;
import kioke.journal.model.Page;
import kioke.journal.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class PageService {
  @Autowired @Lazy private PageRepository pageRepository;

  public Page createPage(Journal journal, String title) {
    Page page =
        Page.builder().journal(journal).date(LocalDate.now()).title(title).content("").build();

    page = pageRepository.save(page);
    return page;
  }

  public Page getPage(String pageId) {
    Page page = pageRepository.findById(pageId).get();
    return page;
  }
}
