package io.kioke.feature.journal.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JournalCleanerService {

  @Scheduled(cron = "0 0 0 * * ?")
  protected void clean() {}
}
