package kioke.journal.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import kioke.journal.configuration.AspectConfiguration;
import kioke.journal.dto.data.journal.JournalPreviewDto;
import kioke.journal.model.Journal;
import kioke.journal.repository.UserRepository;
import kioke.journal.service.AuthService;
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
  @MockBean private UserService userService;
  @MockBean private AuthService authService;

  @MockBean private UserRepository userRepository;

  private final String userId = "bc245ad4-1958-4fb0-80a4-b21b1b0f8b9a";
  private final String journalId = "55bdfaa9-4720-45d1-b97e-8884b1bbca26";

  private Journal journal;

  @BeforeEach
  public void setUp() {
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
  public void test_getJournals_given_whenSuccess_thenReturnStatusOk() throws Exception {
    given(journalService.getJournals(userId, false))
        .willReturn(List.of(JournalPreviewDto.from(journal, false)));

    mockMvc
        .perform(get("/journals"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success", is(true)));
  }

  @AfterEach
  public void cleanUp() {
    SecurityContextHolder.clearContext();
  }
}
