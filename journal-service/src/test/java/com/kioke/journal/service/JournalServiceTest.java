package com.kioke.journal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.model.JournalTest;
import com.kioke.journal.repository.JournalRepository;
import java.util.Optional;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class JournalServiceTest {
  @Autowired @Lazy private JournalService journalService;

  @MockBean JournalRepository journalRepository;

  @Test
  public void test_getJournalById_givenNullJournalId_throwsJournalNotFoundException() {
    String jid = null;
    assertThrows(JournalNotFoundException.class, () -> journalService.getJournalById(jid));
  }

  @Test
  public void test_getJournalById_givenNonExistingJournalId_throwsJournalNotFoundException() {
    String jid = "nonExistingJournalId";

    when(journalRepository.findById(jid)).thenReturn(Optional.empty());

    assertThrows(JournalNotFoundException.class, () -> journalService.getJournalById(jid));
  }

  @Test
  public void test_getJournalById_givenExistingJournalId_returnsCorrectJournal()
      throws JournalNotFoundException {
    String jid = "existingJournalId";

    Journal expected = JournalTest.buildTestData(jid);
    when(journalRepository.findById(jid)).thenReturn(Optional.of(expected));

    Journal actual = journalService.getJournalById(jid);

    assertEquals(expected, actual);
    assertEquals(jid, actual.getId());
  }
}
