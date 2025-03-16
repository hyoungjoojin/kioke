package com.kioke.journal.service;

import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.model.User;
import com.kioke.journal.repository.JournalRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class JournalService {
  @Autowired @Lazy JournalRepository journalRepository;

  public Journal getJournalById(String jid) throws JournalNotFoundException {
    return journalRepository.findById(jid).orElseThrow(() -> new JournalNotFoundException());
  }

  public Journal createJournal(User user, String title, String description) {
    Journal journal =
        Journal.builder()
            .title(title)
            .description(description)
            .users(new ArrayList<>())
            .shelves(new ArrayList<>())
            .isDeleted(false)
            .build();

    journal = journalRepository.save(journal);
    return journal;
  }

  public void updateJournal(Journal journal, String title) {
    if (title != null) {
      journal.setTitle(title);
    }

    journalRepository.save(journal);
  }

  public Optional<Journal> deleteJournal(User user, Journal journal) {
    if (journal.isDeleted()) {
      journalRepository.delete(journal);
      return Optional.empty();
    }

    journal.setDeleted(true);
    journal = journalRepository.save(journal);
    return Optional.of(journal);
  }
}
