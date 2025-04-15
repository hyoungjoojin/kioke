package kioke.journal.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

import kioke.journal.repository.BookmarkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookmarkServiceTests {

  @InjectMocks private BookmarkService bookmarkService;

  @Mock private BookmarkRepository bookmarkRepository;

  private final String userId = "5385d839-e50e-4f40-99c8-464ef3100bc0";
  private final String journalId = "7361b326-5bd8-4f6e-94a1-9fd276445395";

  @Test
  public void isJournalBookmarked_nullParameters_throwNullPointerException() {
    assertThrows(
        NullPointerException.class,
        () -> {
          bookmarkService.isJournalBookmarked(userId, null);
        });

    assertThrows(
        NullPointerException.class,
        () -> {
          bookmarkService.isJournalBookmarked(null, journalId);
        });

    verifyNoInteractions(bookmarkRepository);
  }
}
