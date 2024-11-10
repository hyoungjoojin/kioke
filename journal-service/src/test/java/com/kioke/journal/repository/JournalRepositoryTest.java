package com.kioke.journal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.kioke.journal.model.Journal;
import com.kioke.journal.model.Page;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JournalRepositoryTest {
  @Autowired JournalRepository journalRepository;

  private static Journal buildSampleJournal() {
    return Journal.builder()
        .title(UUID.randomUUID().toString())
        .template(UUID.randomUUID().toString())
        .pages(new ArrayList<Page>())
        .build();
  }

  @Test
  public void test_saveJournal_savesSuccessfullyWithCorrectFields() {
    Journal journalToSave = buildSampleJournal();
    Journal savedJournal = journalRepository.save(journalToSave);

    assertNotNull(savedJournal);
    assertEquals(journalToSave.getTitle(), savedJournal.getTitle());
    assertEquals(journalToSave.getTemplate(), savedJournal.getTemplate());
  }

  @Test
  public void test_saveJournal_savesSuccessfullyWithNonNullId() {
    Journal journalToSave = buildSampleJournal();
    Journal savedJournal = journalRepository.save(journalToSave);

    assertNotNull(savedJournal);
    assertNotNull(savedJournal.getId());
  }

  @Test
  public void test_saveJournal_savesSuccessfullyWithNonNullCreatedAt() {
    Journal journalToSave = buildSampleJournal();
    Journal savedJournal = journalRepository.save(journalToSave);

    assertNotNull(savedJournal);
    assertNotNull(savedJournal.getCreatedAt());
  }

  @Test
  public void test_saveJournal_savesSuccessfullyWithNonNullLastUpdated() {
    Journal journalToSave = buildSampleJournal();
    Journal savedJournal = journalRepository.save(journalToSave);

    assertNotNull(savedJournal);
    assertNotNull(savedJournal.getLastUpdated());
  }
}
