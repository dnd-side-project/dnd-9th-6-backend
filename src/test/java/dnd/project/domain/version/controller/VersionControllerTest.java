package dnd.project.domain.version.controller;

import dnd.project.domain.ControllerTestSupport;
import dnd.project.domain.version.request.VersionRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VersionControllerTest extends ControllerTestSupport {

    @DisplayName("관리자 버전 변경 API")
    @Test
    void updateVersionService() throws Exception {
        // given
        VersionRequest.Update request = new VersionRequest.Update("version 0.0.2", "TEST");
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/version")
                                .content(objectMapper.writeValueAsString(request.toServiceRequest()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("관리자 버전 변경 API -> 관리자 이름 미입력시 실패")
    @Test
    void updateVersionServiceFailWhenAdminNameIsBlank() throws Exception {
        // given
        VersionRequest.Update request = new VersionRequest.Update("version 0.0.2", "");
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/version")
                                .content(objectMapper.writeValueAsString(request.toServiceRequest()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}