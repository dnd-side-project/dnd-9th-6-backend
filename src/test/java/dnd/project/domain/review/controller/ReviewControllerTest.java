package dnd.project.domain.review.controller;

import dnd.project.domain.ControllerTestSupport;
import dnd.project.domain.review.request.ReviewRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends ControllerTestSupport {

    @DisplayName("후기 작성 API")
    @Test
    void createReview() throws Exception {
        // given
        ReviewRequest.Create request = new ReviewRequest.Create(1L, 4.0, List.of("빠른 답변", "이해가 잘돼요", "보통이에요"), null);

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

    @DisplayName("후기 수정 API")
    @Test
    void updateReview() throws Exception {
        // given
        ReviewRequest.Update request =
                new ReviewRequest.Update(1L, 4.0, "빠른 답변,이해가 잘돼요,보통이에요", null);

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/review")
                                .header("Authorization", "Bearer AccessToken")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("후기 좋아요 및 취소 API")
    @Test
    void toggleLikeReview() throws Exception {
        // given
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/review/like")
                                .header("Authorization", "Bearer AccessToken")
                                .param("reviewId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("최근 올라온 후기 조회 API - 로그인")
    @Test
    void readRecentReview() throws Exception {
        // given
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/review/recent")
                                .header("Authorization", "Bearer AccessToken")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("최근 올라온 후기 조회 API - 미로그인")
    @Test
    void readRecentReviewNotLogin() throws Exception {
        // given
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/review/recent")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("내 후기 조회 API")
    @Test
    void readMyReviews() throws Exception {
        // given
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/review")
                                .header("Authorization", "Bearer AccessToken")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("후기 키워드 검색 API")
    @Test
    void readKeywordReview() throws Exception {
        // given
        ReviewRequest.Keyword request = new ReviewRequest.Keyword("커리큘럼과 똑같아요");

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/review/keyword")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("후기 키워드 검색 API - 키워드 미제공 실패")
    @Test
    void readKeywordReviewWithNotInsertKeywordThrowException() throws Exception {
        // given
        ReviewRequest.Keyword request = new ReviewRequest.Keyword(null);

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/review/keyword")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}