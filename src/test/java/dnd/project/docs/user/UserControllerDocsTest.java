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
import static org.springframework.restdocs.payload.JsonFieldType.*;
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

    @DisplayName("소셜 로그인 API")
    @Test
    void loginByKakao() throws Exception {
        // given
        given(userService.loginByOAuth(anyString(), any(Platform.class)))
                .willReturn(
                        UserResponse.Login.builder()
                                .id(1L)
                                .imageUrl("https://lh3.googleusercontent.com/a/AAcHTtcvELyELRF2Mzpi8LEnNN4yTZi5PthvLCskaI_gRG7m=s96-c")
                                .email("dnd-9th-6@gmail.com")
                                .name("클래스코프")
                                .isRegister(true)
                                .accessToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5dS1qdW5nMzE0NzZAbmF2ZXIuY29tIiwiZXhwIjoxNjg5MjYwODM2fQ.cgZ8eFDU_Gz7Z3EghXxoa3v-iXUeQmBZ1AfKCBQZnnqFJ6mqMqGdiTS5uVCF1lIKBarXeD6nEmRZj9Ng94pnHw")
                                .refreshToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5dS1qdW5nMzE0NzZAbmF2ZXIuY29tIiwiZXhwIjoxNjg5MjYwODM2fQ.cgZ8eFDU_Gz7Z3EghXxoa3v-iXUeQmBZ1AfKCBQZnnqFJ6mqMqGdiTS5uVCF1lIKBarXeD6nEmRZj9Ng94pnHw")
                                .build()
                );

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/auth/signin")
                                .param("code", "4%2F0AdEu5BVdKyrEElZENWgSJOrJSHUeAjsSJHvWUSi237TQ13FqUfqPOa-ZcES6lGID7DmVaJwSig")
                                .param("platform","KAKAO")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("login-by-social",
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("code")
                                        .description("소셜 로그인 후 발급된 인가코드"),
                                parameterWithName("platform")
                                        .description("'KAKAO' / 'GOOGLE'")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER)
                                        .description("상태 코드"),
                                fieldWithPath("message").type(STRING)
                                        .description("상태 메세지"),
                                fieldWithPath("data.id").type(NUMBER)
                                        .description("유저 ID / Long"),
                                fieldWithPath("data.imageUrl").type(STRING)
                                        .description("유저 프로필 이미지 URL"),
                                fieldWithPath("data.email").type(STRING)
                                        .description("유저 이메일"),
                                fieldWithPath("data.name").type(STRING)
                                        .description("유저 이름"),
                                fieldWithPath("data.isRegister").type(BOOLEAN)
                                        .description("첫 로그인(회원가입) 여부"),
                                fieldWithPath("data.accessToken").type(STRING)
                                        .description("발급된 JWT AccessToken"),
                                fieldWithPath("data.refreshToken").type(STRING)
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
                                fieldWithPath("interests").type(ARRAY)
                                        .description("선택된 관심분야 / List<String>")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER)
                                        .description("상태 코드"),
                                fieldWithPath("message").type(STRING)
                                        .description("상태 메세지"),
                                fieldWithPath("data").type(NULL)
                                        .description("NULL")
                        )
                ));
    }

    @DisplayName("내 프로필 조회하기 API")
    @Test
    void detailUser() throws Exception {
        // given
        given(userService.detailUser(any()))
                .willReturn(UserResponse.Detail.builder()
                        .id(1L)
                        .email("classcope@gmail.com")
                        .nickName("클래스코프")
                        .imageUrl("http://www.aws.../image.png")
                        .interests("디자인,드로잉")
                        .build());

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/auth")
                                .header("Authorization", "Bearer AccessToken")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("detail-user",
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("발급된 JWT AccessToken")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER)
                                        .description("상태 코드"),
                                fieldWithPath("message").type(STRING)
                                        .description("상태 메세지"),
                                fieldWithPath("data.id").type(NUMBER)
                                        .description("유저 ID"),
                                fieldWithPath("data.email").type(STRING)
                                        .description("유저 이메일"),
                                fieldWithPath("data.nickName").type(STRING)
                                        .description("유저 닉네임"),
                                fieldWithPath("data.imageUrl").type(STRING)
                                        .description("유저 프로필 이미지 URL"),
                                fieldWithPath("data.interests").type(STRING)
                                        .description("유저 관심분야")
                        )
                ));
    }

    @DisplayName("내 정보 수정하기 API")
    @Test
    void updateUser() throws Exception {
        // given
        UserRequest.Update request = new UserRequest.Update("클래스코프", List.of("프로그래밍", "커리어"));
        given(userService.updateUser(any(), any()))
                .willReturn(
                        UserResponse.Detail.builder()
                                .id(1L)
                                .email("classcope@gmail.com")
                                .nickName("클래스코프")
                                .imageUrl("http://www.aws.../image.png")
                                .interests("프로그래밍,커리어")
                                .build()
                );

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.patch("/auth")
                                .header("Authorization", "Bearer AccessToken")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("update-user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("발급된 JWT AccessToken")
                        ),
                        requestFields(
                                fieldWithPath("nickName").type(STRING)
                                        .description("변경할 닉네임"),
                                fieldWithPath("interests").type(ARRAY)
                                        .description("변경할 관심분야 리스트")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER)
                                        .description("상태 코드"),
                                fieldWithPath("message").type(STRING)
                                        .description("상태 메세지"),
                                fieldWithPath("data.id").type(NUMBER)
                                        .description("유저 ID"),
                                fieldWithPath("data.email").type(STRING)
                                        .description("유저 이메일"),
                                fieldWithPath("data.nickName").type(STRING)
                                        .description("유저 닉네임"),
                                fieldWithPath("data.imageUrl").type(STRING)
                                        .description("유저 프로필 이미지 URL"),
                                fieldWithPath("data.interests").type(STRING)
                                        .description("유저 관심분야")
                        )
                ));
    }
}
