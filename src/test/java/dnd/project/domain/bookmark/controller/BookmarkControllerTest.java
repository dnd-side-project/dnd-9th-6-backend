package dnd.project.domain.bookmark.controller;

import dnd.project.domain.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookmarkControllerTest extends ControllerTestSupport {

    @DisplayName("북마크 등록 API")
    @Test
    void addBookmark() throws Exception {
        // given
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/auth/bookmark")
                                .header("Authorization", "Bearer AccessToken")
                                .param("lectureId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("북마크 취소 API")
    @Test
    void cancelBookmark() throws Exception {
        // given
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/auth/bookmark")
                                .header("Authorization", "Bearer AccessToken")
                                .param("lectureId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}