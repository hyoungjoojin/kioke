package kioke.journal.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

import kioke.journal.repository.JournalRepository;
import kioke.journal.repository.UserJournalMetadataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JournalServiceTests {

  @InjectMocks private JournalService journalService;

  @Mock private JournalRepository journalRepository;
  @Mock private UserJournalMetadataRepository userJournalMetadataRepository;

  @Test
  public void test_getJournals_givenNullUserId_whenGetJournals_thenThrowIllegalArgumentException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          journalService.getJournals(null, false);
        });

    verifyNoInteractions(journalRepository);
  }
}
