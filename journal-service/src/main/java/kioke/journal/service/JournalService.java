package kioke.journal.service;

import java.util.ArrayList;
import java.util.Optional;
import kioke.journal.exception.journal.JournalNotFoundException;
import kioke.journal.model.Journal;
import kioke.journal.model.User;
import kioke.journal.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class JournalService {
  @Autowired @Lazy JournalRepository journalRepository;

  public Journal getJournalById(String journalId) throws JournalNotFoundException {
    return journalRepository
        .findById(journalId)
        .orElseThrow(() -> new JournalNotFoundException(journalId));
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
