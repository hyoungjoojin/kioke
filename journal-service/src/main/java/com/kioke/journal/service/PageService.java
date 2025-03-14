package com.kioke.journal.service;

import com.kioke.journal.model.Journal;
import com.kioke.journal.model.Page;
import com.kioke.journal.repository.PageRepository;
import java.time.LocalDate;
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
