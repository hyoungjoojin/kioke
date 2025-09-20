package io.kioke.feature.journal.service;

import io.kioke.feature.journal.repository.JournalRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JournalCleanerService {

  private final JournalRepository journalRepository;

  public JournalCleanerService(JournalRepository journalRepository) {
    this.journalRepository = journalRepository;
  }

  @Scheduled(cron = "0 0 0 * * ?")
  protected void clean() {
    journalRepository.deleteJournalsOlderThen30Days();
  }
}
