package com.kioke.auth.service;

import com.kioke.auth.model.Journal;
import com.kioke.auth.repository.JournalRepository;
import java.util.NoSuchElementException;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalService {
  @Autowired @Lazy JournalRepository journalRepository;

  public Journal getJournalById(String jid) {
    try {
      return journalRepository.findById(jid).get();
    } catch (NoSuchElementException e) {
      return journalRepository.save(Journal.builder().jid(jid).build());
    }
  }
}
