package dnd.project.domain.review.controller;

import dnd.project.domain.ControllerTestSupport;
import dnd.project.domain.review.request.ReviewRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends ControllerTestSupport {

    @DisplayName("후기 작성 API")
    @Test
    void createReview() throws Exception {
        // given
        ReviewRequest.Create request = new ReviewRequest.Create(1L, 4.0, "빠른 답변,이해가 잘돼요,보통이에요", null);

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/review")
                                .header("Authorization", "Bearer AccessToken")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("후기 삭제 API")
    @Test
    void deleteReview() throws Exception {
        // given
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/review")
                                .header("Authorization", "Bearer AccessToken")
                                .param("reviewId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}