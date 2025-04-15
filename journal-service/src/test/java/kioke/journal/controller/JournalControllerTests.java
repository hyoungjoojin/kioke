package kioke.journal.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import kioke.commons.constant.ErrorCode;
import kioke.journal.configuration.AspectConfiguration;
import kioke.journal.dto.data.journal.JournalDto;
import kioke.journal.exception.journal.JournalNotFoundException;
import kioke.journal.model.Journal;
import kioke.journal.repository.JournalRoleRepository;
import kioke.journal.repository.UserRepository;
import kioke.journal.service.AuthService;
import kioke.journal.service.BookmarkService;
import kioke.journal.service.JournalService;
import kioke.journal.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(JournalController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({AspectConfiguration.class})
public class JournalControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private JournalService journalService;
  @MockBean private BookmarkService bookmarkService;
  @MockBean private UserService userService;
  @MockBean private AuthService authService;

  @MockBean private JournalRoleRepository journalRoleRepository;
  @MockBean private UserRepository userRepository;

  private final String userId = "bc245ad4-1958-4fb0-80a4-b21b1b0f8b9a";
  private final String journalId = "55bdfaa9-4720-45d1-b97e-8884b1bbca26";

  private Journal journal;

  @BeforeEach
  public void setup() {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    journal =
        Journal.builder()
            .journalId(journalId)
            .users(Collections.emptyList())
            .pages(Collections.emptyList())
            .build();
  }

  @Test
  public void getJournalById_journalDoesNotExist_statusNotFound() throws Exception {
    given(journalService.getJournalById(userId, journalId))
        .willThrow(new JournalNotFoundException(journalId));

    mockMvc
        .perform(get("/journals/{journalId}", journalId))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success", is(false)))
        .andExpect(jsonPath("$.error.code", is(ErrorCode.JOURNAL_NOT_FOUND.getCode())));
  }

  @Test
  public void getJournalById_success_statusOk() throws Exception {
    given(journalService.getJournalById(userId, journalId)).willReturn(JournalDto.from(journal, true));

    given(bookmarkService.isJournalBookmarked(userId, journalId)).willReturn(true);

    mockMvc
        .perform(get("/journals/{journalId}", journalId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.data.journalId", is(journalId)))
        .andExpect(jsonPath("$.data.bookmarked", is(true)));

    given(journalService.getJournalById(userId, journalId)).willReturn(JournalDto.from(journal, false));
    given(bookmarkService.isJournalBookmarked(userId, journalId)).willReturn(false);

    mockMvc
        .perform(get("/journals/{journalId}", journalId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.bookmarked", is(false)));
  }

  @AfterEach
  public void cleanup() {
    SecurityContextHolder.clearContext();
  }
}
