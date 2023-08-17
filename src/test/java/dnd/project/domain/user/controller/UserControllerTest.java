package dnd.project.domain.user.controller;

import dnd.project.domain.ControllerTestSupport;
import dnd.project.domain.user.request.controller.UserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTestSupport {

    @DisplayName("소셜 로그인 API")
    @Test
    void loginByKakao() throws Exception {
        // given
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/auth/signin")
                                .param("code", "4%2F0AdEu5BVdKyrEElZENWgSJOrJSHUeAjsSJHvWUSi237TQ13FqUfqPOa-ZcES6lGID7DmVaJwSig")
                                .param("platform","KAKAO")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("관심분야 추가 요청 API")
    @Test
    void addInterests() throws Exception {
        // given
        UserRequest.Interests request = new UserRequest.Interests(List.of("데이터 사이언스,디자인"));
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/auth")
                                .header("Authorization", "Bearer AccessToken")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("내 프로필 조회하기 API")
    @Test
    void detailUser() throws Exception {
        // given
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/auth")
                                .header("Authorization", "Bearer AccessToken")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("내 정보 수정하기 API")
    @Test
    void updateUser() throws Exception {
        // given
        UserRequest.Update request = new UserRequest.Update("클래스코프", List.of("프로그래밍", "커리어"));

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/auth")
                                .header("Authorization", "Bearer AccessToken")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("내 정보 수정하기 API - 닉네임 미입력 실패")
    @Test
    void updateUserNotInsertNickNameThrowException() throws Exception {
        // given
        UserRequest.Update request = new UserRequest.Update(" ", List.of("프로그래밍", "커리어"));

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/auth")
                                .header("Authorization", "Bearer AccessToken")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("내 정보 수정하기 API - 관심분야 미입력 실패")
    @Test
    void updateUserNotInsertInterestThrowException() throws Exception {
        // given
        UserRequest.Update request = new UserRequest.Update("클래스코프", null);

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/auth")
                                .header("Authorization", "Bearer AccessToken")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}