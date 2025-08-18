package io.kioke.feature.profile.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import autoparams.AutoParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kioke.annotation.MockAuthenticatedUser;
import io.kioke.config.WebMvcConfig;
import io.kioke.feature.profile.dto.ProfileDto;
import io.kioke.feature.profile.dto.request.UpdateProfileRequestDto;
import io.kioke.feature.profile.dto.response.GetMyProfileResponseDto;
import io.kioke.feature.profile.service.ProfileService;
import io.kioke.feature.profile.util.ProfileMapper;
import io.kioke.feature.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProfileController.class)
@Import(WebMvcConfig.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public class ProfileControllerTests {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private ProfileService profileService;
  @MockitoBean private ProfileMapper profileMapper;

  private static final UserDto user = new UserDto("userId");

  @Test
  @MockAuthenticatedUser
  @AutoParams
  public void getMyProfile_success_statusOk(ProfileDto profile, GetMyProfileResponseDto response)
      throws Exception {
    given(profileService.getMyProfile(user)).willReturn(profile);
    given(profileMapper.toGetMyProfileResponse(profile)).willReturn(response);

    mockMvc
        .perform(get("/users/me"))
        .andExpect(status().isOk())
        .andDo(
            document(
                "getMyProfile",
                responseFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description(""),
                    fieldWithPath("name").type(JsonFieldType.STRING).description(""),
                    fieldWithPath("onboarded").type(JsonFieldType.BOOLEAN).description(""),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description(""))));
  }

  @Test
  @MockAuthenticatedUser
  @AutoParams
  public void updateProfile_success_statusNoContent(UpdateProfileRequestDto requestBody)
      throws Exception {
    mockMvc
        .perform(
            put("/users/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestBody)))
        .andExpect(status().isNoContent())
        .andDo(
            document(
                "updateProfile",
                requestFields(
                    fieldWithPath("name").optional().type(JsonFieldType.STRING).description(""),
                    fieldWithPath("onboarded")
                        .optional()
                        .type(JsonFieldType.BOOLEAN)
                        .description(""))));
  }
}
