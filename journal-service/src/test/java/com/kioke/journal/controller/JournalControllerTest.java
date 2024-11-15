package com.kioke.journal.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kioke.journal.constant.ErrorCode;
import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.model.JournalTest;
import com.kioke.journal.service.JournalService;
import java.time.ZoneOffset;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class JournalControllerTest {
  @Autowired @Lazy private JournalController journalController;

  @Autowired @Lazy private MockMvc mockMvc;
  @MockBean private JournalService journalService;

  @Test
  public void test_getJournalById_givenExistingJournalId_returnsJournalWith200()
      throws JournalNotFoundException, Exception {
    String jid = "existingJournalId";
    String path = String.format("/journals/%s", jid);

    Journal journal = JournalTest.buildTestData(jid);
    when(journalService.getJournalById(jid)).thenReturn(journal);

    mockMvc
        .perform(get(path))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.requestId").exists())
        .andExpect(jsonPath("$.path").value(path))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.status").value(200))
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.jid").value(journal.getId()))
        .andExpect(jsonPath("$.data.title").value(journal.getTitle()))
        .andExpect(jsonPath("$.data.template").value(journal.getTemplate()))
        .andExpect(
            jsonPath("$.data.createdAt")
                .value(journal.getCreatedAt().atOffset(ZoneOffset.UTC).toString()))
        .andExpect(
            jsonPath("$.data.lastUpdated")
                .value(journal.getLastUpdated().atOffset(ZoneOffset.UTC).toString()));
  }

  @Test
  public void test_getJournalById_givenNonExistingJournal_returnsErrorWith404()
      throws JournalNotFoundException, Exception {
    String jid = "nonExistingJournalId";
    String path = String.format("/journals/%s", jid);

    when(journalService.getJournalById(jid)).thenThrow(JournalNotFoundException.class);

    mockMvc
        .perform(get(path))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
        .andExpect(jsonPath("$.requestId").exists())
        .andExpect(jsonPath("$.path").value(path))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.error.type").value(ErrorCode.JOURNAL_NOT_FOUND.getType()))
        .andExpect(jsonPath("$.error.title").value(ErrorCode.JOURNAL_NOT_FOUND.getTitle()))
        .andExpect(jsonPath("$.error.detail").exists())
        .andExpect(jsonPath("$.error.instance").value(path));
  }

  @Test
  public void test_getJournalById_unknownExceptionOccursInService_returnsErrorWith500()
      throws JournalNotFoundException, Exception {
    String jid = "anyJournalId";
    String path = String.format("/journals/%s", jid);

    when(journalService.getJournalById(jid)).thenThrow(RuntimeException.class);

    mockMvc
        .perform(get(path))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
        .andExpect(jsonPath("$.requestId").exists())
        .andExpect(jsonPath("$.path").value(path))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.status").value(500))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.error.type").value(ErrorCode.INTERNAL_SERVER_ERROR.getType()))
        .andExpect(jsonPath("$.error.title").value(ErrorCode.INTERNAL_SERVER_ERROR.getTitle()))
        .andExpect(jsonPath("$.error.detail").exists())
        .andExpect(jsonPath("$.error.instance").value(path));
  }
}
