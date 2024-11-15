package com.kioke.journal.service;

import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class JournalService {
  @Autowired @Lazy private JournalRepository journalRepository;

  public Journal getJournalById(String jid) throws JournalNotFoundException {
    try {
      Journal journal =
          journalRepository.findById(jid).orElseThrow(() -> new JournalNotFoundException(jid));

      return journal;
    } catch (IllegalArgumentException e) {
      throw new JournalNotFoundException(jid);
    }
  }
}
