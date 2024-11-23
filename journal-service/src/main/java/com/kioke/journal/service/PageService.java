package com.kioke.journal.service;

import com.kioke.journal.dto.data.page.CreatePageDto;
import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.model.Block;
import com.kioke.journal.model.Journal;
import com.kioke.journal.model.Page;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class PageService {
  @Autowired @Lazy private JournalService journalService;

  public Page createPage(String jid, CreatePageDto createPageDto)
      throws JournalNotFoundException, Exception {
    Journal journal = journalService.getJournalById(jid);

    LocalDate date = createPageDto.getDate().orElse(LocalDate.now());
    String template = createPageDto.getTemplate().orElse(journal.getTemplate());

    Page pageToSave =
        Page.builder()
            .jid(jid)
            .template(template)
            .date(date)
            .blocks(new ArrayList<Block>())
            .build();
    journal.getPages().add(pageToSave);

    return pageToSave;
  }
}
