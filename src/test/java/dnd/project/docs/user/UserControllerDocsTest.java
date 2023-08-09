package dnd.project.docs.user;

import dnd.project.docs.RestDocsSupport;
import dnd.project.domain.user.config.Platform;
import dnd.project.domain.user.controller.UserController;
import dnd.project.domain.user.request.controller.UserRequest;
import dnd.project.domain.user.response.UserResponse;
import dnd.project.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerDocsTest extends RestDocsSupport {

    private final UserService userService = mock(UserService.class);

    @Override
    protected Object initController() {
        return new UserController(userService);
    }

    @DisplayName("카카오 로그인 API")
    @Test
    void loginByKakao() throws Exception {
        // given
        given(userService.loginByOAuth(anyString(), any(Platform.class)))
                .willReturn(
                        UserResponse.Login.builder()
                                .id(1L)
                                .email("dnd-9th-6@gmail.com")
                                .name("클래스코프")
                                .isRegister(true)
                                .accessToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5dS1qdW5nMzE0NzZAbmF2ZXIuY29tIiwiZXhwIjoxNjg5MjYwODM2fQ.cgZ8eFDU_Gz7Z3EghXxoa3v-iXUeQmBZ1AfKCBQZnnqFJ6mqMqGdiTS5uVCF1lIKBarXeD6nEmRZj9Ng94pnHw")
                                .refreshToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5dS1qdW5nMzE0NzZAbmF2ZXIuY29tIiwiZXhwIjoxNjg5MjYwODM2fQ.cgZ8eFDU_Gz7Z3EghXxoa3v-iXUeQmBZ1AfKCBQZnnqFJ6mqMqGdiTS5uVCF1lIKBarXeD6nEmRZj9Ng94pnHw")
                                .build()
                );

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/login/kakao")
                                .param("code", "4%2F0AdEu5BVdKyrEElZENWgSJOrJSHUeAjsSJHvWUSi237TQ13FqUfqPOa-ZcES6lGID7DmVaJwSig")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("login-by-kakao",
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("code")
                                        .description("소셜 로그인 후 발급된 인가코드")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("상태 메세지"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                                        .description("유저 ID / Long"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING)
                                        .description("유저 이메일"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING)
                                        .description("유저 이름"),
                                fieldWithPath("data.isRegister").type(JsonFieldType.BOOLEAN)
                                        .description("첫 로그인(회원가입) 여부"),
                                fieldWithPath("data.accessToken").type(JsonFieldType.STRING)
                                        .description("발급된 JWT AccessToken"),
                                fieldWithPath("data.refreshToken").type(JsonFieldType.STRING)
                                        .description("발급된 JWT RefreshToken")
                        )
                ));
    }

    @DisplayName("관심분야 추가 요청 API")
    @Test
    void addInterests() throws Exception {
        // given
        UserRequest.Interests request = new UserRequest.Interests(List.of("데이터 사이언스,디자인"));

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/auth")
                                .header("Authorization", "Bearer AccessToken")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("add-interests",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("발급된 JWT AccessToken")
                        ),
                        requestFields(
                                fieldWithPath("interests").type(JsonFieldType.ARRAY)
                                        .description("선택된 관심분야 / List<String>")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("상태 메세지"),
                                fieldWithPath("data").type(JsonFieldType.NULL)
                                        .description("NULL")
                        )
                ));
    }
}
