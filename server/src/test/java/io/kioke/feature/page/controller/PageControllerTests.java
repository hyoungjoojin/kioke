package io.kioke.feature.page.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import autoparams.AutoParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kioke.annotation.MockAuthenticatedUser;
import io.kioke.config.WebMvcConfig;
import io.kioke.feature.page.dto.PageDto;
import io.kioke.feature.page.dto.request.CreatePageRequestDto;
import io.kioke.feature.page.dto.request.UpdatePageRequestDto;
import io.kioke.feature.page.dto.response.CreatePageResponseDto;
import io.kioke.feature.page.dto.response.GetPageResponseDto;
import io.kioke.feature.page.service.PageService;
import io.kioke.feature.page.util.PageMapper;
import io.kioke.feature.user.dto.UserDto;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PageController.class)
@Import(WebMvcConfig.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public class PageControllerTests {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private PageService pageService;
  @MockitoBean private PageMapper pageMapper;

  private UserDto authenticatedUser = new UserDto("userId");

  @Test
  @MockAuthenticatedUser
  @AutoParams
  public void getPage_success_statusOk(String pageId, PageDto page, GetPageResponseDto response)
      throws Exception {
    when(pageService.getPage(authenticatedUser, pageId)).thenReturn(page);
    when(pageMapper.toGetPageResponse(page)).thenReturn(response);

    mockMvc
        .perform(get("/pages/{pageId}", pageId))
        .andExpect(status().isOk())
        .andDo(
            document(
                "getPage",
                pathParameters(parameterWithName("pageId").description("Page ID")),
                responseFields(
                    fieldWithPath("pageId").description("Page ID").type(String.class),
                    fieldWithPath("journalId").description("Journal ID of page").type(String.class),
                    fieldWithPath("title").description("Page title").type(String.class),
                    fieldWithPath("content").description("Page content").type(String.class),
                    fieldWithPath("date").description("Page date").type(LocalDateTime.class))));
  }

  @Test
  @MockAuthenticatedUser
  @AutoParams
  public void createPage_success_statusCreated(
      CreatePageRequestDto request, PageDto page, CreatePageResponseDto response) throws Exception {
    when(pageService.createPage(authenticatedUser, request)).thenReturn(page);
    when(pageMapper.toCreatePageResponse(page)).thenReturn(response);

    mockMvc
        .perform(
            post("/pages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isCreated())
        .andDo(
            document(
                "createPage",
                requestFields(
                    fieldWithPath("journalId").description("Journal ID").type(String.class),
                    fieldWithPath("title").description("Page title").type(String.class),
                    fieldWithPath("date").description("Page date").type(LocalDateTime.class)),
                responseFields(fieldWithPath("pageId").description("Page ID").type(String.class))));
  }

  @Test
  @MockAuthenticatedUser
  @AutoParams
  public void updatePage_success_statusNoContent(String pageId, UpdatePageRequestDto request)
      throws Exception {
    mockMvc
        .perform(
            put("/pages/{pageId}", pageId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isNoContent())
        .andDo(
            document(
                "getPage",
                requestFields(
                    fieldWithPath("journalId").description("Journal ID").type(String.class),
                    fieldWithPath("title").description("Page title").type(String.class).optional(),
                    fieldWithPath("content")
                        .description("Page content")
                        .type(String.class)
                        .optional(),
                    fieldWithPath("date")
                        .description("Page date")
                        .type(LocalDateTime.class)
                        .optional())));
  }
}
