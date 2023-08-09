package dnd.project.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dnd.project.domain.ControllerTestSupport;
import dnd.project.domain.user.request.controller.UserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTestSupport {

    @DisplayName("카카오 로그인 API")
    @Test
    void loginByKakao() throws Exception {
        // given
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/login/kakao")
                                .param("code", "4%2F0AdEu5BVdKyrEElZENWgSJOrJSHUeAjsSJHvWUSi237TQ13FqUfqPOa-ZcES6lGID7DmVaJwSig")
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
}