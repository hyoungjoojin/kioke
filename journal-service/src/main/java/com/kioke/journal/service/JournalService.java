package com.kioke.journal.service;

import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.model.Shelf;
import com.kioke.journal.model.User;
import com.kioke.journal.repository.JournalRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class JournalService {
  @Autowired @Lazy JournalRepository journalRepository;

  public Journal getJournalById(String jid) throws JournalNotFoundException {
    return journalRepository.findById(jid).orElseThrow(() -> new JournalNotFoundException(jid));
  }

  public Journal createJournal(User user, Shelf shelf, String title) {
    Journal journal = Journal.builder().title(title).users(new ArrayList<>()).shelf(shelf).build();
    journal = journalRepository.save(journal);
    return journal;
  }

  public void deleteJournal(Journal journal) {
    journalRepository.delete(journal);
  }
}
