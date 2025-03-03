package com.kioke.journal.service;

import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.model.Shelf;
import com.kioke.journal.model.User;
import com.kioke.journal.repository.JournalRepository;
import com.kioke.journal.repository.ShelfRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class JournalService {
  @Autowired @Lazy JournalRepository journalRepository;
  @Autowired @Lazy ShelfRepository shelfRepository;

  public Journal getJournalById(String jid) throws JournalNotFoundException {
    return journalRepository.findById(jid).orElseThrow(() -> new JournalNotFoundException());
  }

  public Journal createJournal(User user, Shelf shelf, String title) {
    Journal journal =
        Journal.builder()
            .title(title)
            .users(new ArrayList<>())
            .shelf(shelf)
            .isDeleted(false)
            .build();

    journal = journalRepository.save(journal);
    return journal;
  }

  public void deleteJournal(User user, Journal journal) {
    if (journal.isDeleted()) {
      journalRepository.delete(journal);
      return;
    }

    Shelf archive =
        shelfRepository
            .findByOwnerAndIsArchiveTrue(user)
            .orElseThrow(() -> new IllegalStateException("User does not have archive shelf."));

    journal.setDeleted(true);
    journal.setShelf(archive);
    journalRepository.save(journal);
  }
}
