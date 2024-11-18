package com.kioke.journal.service;

import com.kioke.journal.dto.data.journal.CreateJournalDto;
import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.model.Page;
import com.kioke.journal.repository.JournalRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class JournalService {
  @Autowired @Lazy private JournalRepository journalRepository;

  public Journal createJournal(CreateJournalDto createJournalDto) {
    String title = createJournalDto.getTitle(), template = createJournalDto.getTemplate();

    Journal journalToSave =
        Journal.builder().title(title).template(template).pages(new ArrayList<Page>()).build();

    Journal savedJournal = journalRepository.save(journalToSave);
    return savedJournal;
  }

  public Journal getJournalById(String jid) throws JournalNotFoundException {
    try {
      Journal journal =
          journalRepository.findById(jid).orElseThrow(() -> new JournalNotFoundException(jid));

      return journal;
    } catch (IllegalArgumentException e) {
      throw new JournalNotFoundException(jid);
    }
  }

  public void deleteJournalById(String jid) throws JournalNotFoundException {
    try {
      journalRepository.findById(jid).orElseThrow(() -> new JournalNotFoundException(jid));
    } catch (IllegalArgumentException e) {
      throw new JournalNotFoundException(jid);
    }

    journalRepository.deleteById(jid);
  }
}
