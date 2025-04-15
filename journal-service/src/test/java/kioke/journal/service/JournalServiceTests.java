package kioke.journal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Optional;
import kioke.journal.constant.Permission;
import kioke.journal.exception.journal.JournalNotFoundException;
import kioke.journal.model.Journal;
import kioke.journal.repository.JournalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JournalServiceTests {

  @InjectMocks private JournalService journalService;

  @Mock private JournalRoleService journalRoleService;

  @Mock private JournalRepository journalRepository;

  private final String userId = "5385d839-e50e-4f40-99c8-464ef3100bc0";
  private final String journalId = "7361b326-5bd8-4f6e-94a1-9fd276445395";

  private Journal journal;

  @BeforeEach
  public void setup() {
    journal = Journal.builder().journalId(journalId).build();
  }

  @Test
  public void getJournalById_nullParameters_throwNullPointerException() {
    assertThrows(
        NullPointerException.class,
        () -> {
          journalService.getJournalById(userId, null);
        });

    assertThrows(
        NullPointerException.class,
        () -> {
          journalService.getJournalById(null, journalId);
        });

    verifyNoInteractions(journalRepository);
  }

  @Test
  public void getJournalById_noReadPermission_throwJournalNotFound() {
    given(journalRoleService.hasPermission(userId, journalId, Permission.READ)).willReturn(false);

    assertThrows(
        JournalNotFoundException.class,
        () -> {
          journalService.getJournalById(userId, journalId);
        });

    verifyNoInteractions(journalRepository);
  }

  @Test
  public void getJournalById_journalDoesNotExist_throwJournalNotFound() {
    given(journalRoleService.hasPermission(userId, journalId, Permission.READ)).willReturn(true);
    given(journalRepository.findById(journalId)).willReturn(Optional.empty());

    assertThrows(
        JournalNotFoundException.class,
        () -> {
          journalService.getJournalById(userId, journalId);
        });
  }

  @Test
  public void getJournalById_success_returnJournal() throws Exception {
    given(journalRoleService.hasPermission(userId, journalId, Permission.READ)).willReturn(true);
    given(journalRepository.findById(journalId)).willReturn(Optional.of(journal));

    assertEquals(journalService.getJournalById(userId, journalId), journal);
  }

  @Test
  public void createJournal_success_returnJournal() throws Exception {
    String title = "Title", description = "Description";
    journal.setTitle(title);
    journal.setDescription(description);

    given(journalRepository.save(journal)).willReturn(journal);

    assertEquals(journalService.createJournal(userId, null, title, description), journal);
  }
}
