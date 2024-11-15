package com.kioke.journal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.kioke.journal.model.Journal;
import com.kioke.journal.model.JournalTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JournalRepositoryTest {
  @Autowired JournalRepository journalRepository;

  @Test
  public void test_saveJournal_savesSuccessfullyWithCorrectFields() {
    String jid = "anyJournalId";
    Journal expected = JournalTest.buildTestData(jid);

    Journal actual = journalRepository.save(expected);

    assertNotNull(actual);
    assertEquals(expected, actual);
  }

  @Test
  public void test_saveJournal_savesSuccessfullyWithNonNullFields() {
    String jid = "anyJournalId";
    Journal expected = JournalTest.buildTestData(jid);

    Journal actual = journalRepository.save(expected);

    assertNotNull(actual);
    assertNotNull(actual.getId());
    assertNotNull(actual.getCreatedAt());
    assertNotNull(actual.getLastUpdated());
  }
}
