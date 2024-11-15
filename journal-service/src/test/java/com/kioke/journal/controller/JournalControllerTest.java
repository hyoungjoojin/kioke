package com.kioke.journal.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kioke.journal.constant.ErrorCode;
import com.kioke.journal.dto.request.CreateJournalRequestBodyDto;
import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.model.JournalTest;
import com.kioke.journal.service.JournalService;
import java.time.ZoneOffset;
import java.util.HashMap;
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
  @Autowired @Lazy private ObjectMapper objectMapper;
  @MockBean private JournalService journalService;

  @Test
  public void test_createJournal_givenValidInput_returnsJournalWith201() throws Exception {
    String path = "/journals";

    Journal expected = JournalTest.buildTestData("testJid");

    String expectedTitle = "expectedTitle", expectedTemplate = "expectedTemplate";
    expected.setTitle(expectedTitle);
    expected.setTemplate(expectedTemplate);

    when(journalService.createJournal(expectedTitle, expectedTemplate)).thenReturn(expected);

    String content =
        objectMapper.writeValueAsString(
            CreateJournalRequestBodyDto.builder()
                .title(expectedTitle)
                .template(expectedTemplate)
                .build());

    mockMvc
        .perform(post(path).contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.requestId").exists())
        .andExpect(jsonPath("$.path").value(path))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.status").value(201))
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.jid").exists())
        .andExpect(jsonPath("$.data.title").value(expectedTitle))
        .andExpect(jsonPath("$.data.template").value(expectedTemplate))
        .andExpect(jsonPath("$.data.createdAt").exists())
        .andExpect(jsonPath("$.data.lastUpdated").exists());
  }

  @Test
  public void test_createJournal_givenInvalidInput_returnsErrorWith400() throws Exception {
    String path = "/journals";

    HashMap<String, String> contentMap = new HashMap<>();
    contentMap.put("titl", "Invalid title key");
    contentMap.put("template", "Template");

    String content = objectMapper.writeValueAsString(contentMap);

    mockMvc
        .perform(post(path).contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
        .andExpect(jsonPath("$.requestId").exists())
        .andExpect(jsonPath("$.path").value(path))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.error.type").value(ErrorCode.BAD_REQUEST.getType()))
        .andExpect(jsonPath("$.error.title").value(ErrorCode.BAD_REQUEST.getTitle()))
        .andExpect(jsonPath("$.error.detail").exists())
        .andExpect(jsonPath("$.error.instance").value(path));
  }

  @Test
  public void test_createJournal_unknownExceptionOccursInService_returnsErrorWith500()
      throws JournalNotFoundException, Exception {
    String path = "/journals";

    String title = "title", template = "template";
    when(journalService.createJournal(title, template)).thenThrow(RuntimeException.class);

    String content =
        objectMapper.writeValueAsString(
            CreateJournalRequestBodyDto.builder().title(title).template(template).build());

    mockMvc
        .perform(post(path).contentType(MediaType.APPLICATION_JSON).content(content))
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

  @Test
  public void test_deleteJournalById_givenExistingJournalId_returnsJournalWith200()
      throws JournalNotFoundException, Exception {
    String jid = "existingJournalId";
    String path = String.format("/journals/%s", jid);

    doNothing().when(journalService).deleteJournalById(jid);

    mockMvc
        .perform(delete(path))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.requestId").exists())
        .andExpect(jsonPath("$.path").value(path))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.status").value(200))
        .andExpect(jsonPath("$.success").value(true));
  }

  @Test
  public void test_deleteJournalById_givenNonExistingJournal_returnsErrorWith404()
      throws JournalNotFoundException, Exception {
    String jid = "nonExistingJournalId";
    String path = String.format("/journals/%s", jid);

    doThrow(JournalNotFoundException.class).when(journalService).deleteJournalById(jid);

    mockMvc
        .perform(delete(path))
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
  public void test_deleteJournalById_unknownExceptionOccursInService_returnsErrorWith500()
      throws JournalNotFoundException, Exception {
    String jid = "anyJournalId";
    String path = String.format("/journals/%s", jid);

    doThrow(RuntimeException.class).when(journalService).deleteJournalById(jid);

    mockMvc
        .perform(delete(path))
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
