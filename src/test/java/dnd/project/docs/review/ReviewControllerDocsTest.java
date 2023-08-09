package dnd.project.docs.review;


import dnd.project.docs.RestDocsSupport;
import dnd.project.domain.review.controller.ReviewController;
import dnd.project.domain.review.request.ReviewRequest;
import dnd.project.domain.review.request.ReviewServiceRequest;
import dnd.project.domain.review.response.ReviewResponse;
import dnd.project.domain.review.service.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.formParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewControllerDocsTest extends RestDocsSupport {

    private final ReviewService reviewService = mock(ReviewService.class);

    @Override
    protected Object initController() {
        return new ReviewController(reviewService);
    }

    @DisplayName("후기 작성 API")
    @Test
    void createReview() throws Exception {
        // given
        ReviewRequest.Create request =
                new ReviewRequest.Create(1L, 5.0, "빠른 답변,이해가 잘돼요,보통이에요", "강의가 만족스럽습니다!");

        given(reviewService.createReview(any(), any()))
                .willReturn(
                        ReviewResponse.Create.builder()
                                .reviewId(1L)
                                .lectureId(1L)
                                .userId(1L)
                                .nickName("클래스코프")
                                .score(5.0)
                                .tags("빠른 답변,이해가 잘돼요,보통이에요")
                                .content("강의가 만족스럽습니다!")
                                .createdDate("2023-08-09")
                                .build()
                );

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/review")
                                .header("Authorization", "Bearer AccessToken")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("create-review",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("발급된 JWT AccessToken")
                        ),
                        requestFields(
                                fieldWithPath("lectureId").type(NUMBER)
                                        .description("강의 ID"),
                                fieldWithPath("score").type(NUMBER)
                                        .description("후기 점수 / Double 1~5 "),
                                fieldWithPath("tags").type(STRING)
                                        .description("후기 태그"),
                                fieldWithPath("content").type(STRING)
                                        .description("후기 내용")
                                        .optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER)
                                        .description("상태 코드"),
                                fieldWithPath("message").type(STRING)
                                        .description("상태 메세지"),
                                fieldWithPath("data.reviewId").type(NUMBER)
                                        .description("후기 ID"),
                                fieldWithPath("data.lectureId").type(NUMBER)
                                        .description("강의 ID"),
                                fieldWithPath("data.userId").type(NUMBER)
                                        .description("유저 ID"),
                                fieldWithPath("data.nickName").type(STRING)
                                        .description("유저 닉네임"),
                                fieldWithPath("data.score").type(NUMBER)
                                        .description("후기 점수 / Double"),
                                fieldWithPath("data.tags").type(STRING)
                                        .description("후기 태그"),
                                fieldWithPath("data.content").type(STRING)
                                        .description("후기 내용 / 없을시 빈 문자열"),
                                fieldWithPath("data.createdDate").type(STRING)
                                        .description("후기 작성 날짜")
                        )
                ));
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
                .andExpect(status().isOk())
                .andDo(document("delete-review",
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("발급된 JWT AccessToken")
                        ),
                        formParameters(
                                parameterWithName("reviewId")
                                        .description("후기 ID")
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

    @DisplayName("후기 수정 API")
    @Test
    void updateReview() throws Exception {
        // given
        given(reviewService.updateReview(any(), any()))
                .willReturn(
                        ReviewResponse.Create.builder()
                                .reviewId(1L)
                                .lectureId(1L)
                                .userId(1L)
                                .nickName("클래스코프")
                                .score(5.0)
                                .tags("빠른 답변,이해가 잘돼요,보통이에요")
                                .content("강의가 만족스럽습니다!")
                                .createdDate("2023-08-09")
                                .build()
                );

        ReviewRequest.Update request =
                new ReviewRequest.Update(1L, 4.0, "빠른 답변,이해가 잘돼요,보통이에요", "강의가 알차고 재밌습니다!");

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.patch("/review")
                                .header("Authorization", "Bearer AccessToken")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("update-review",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("발급된 JWT AccessToken")
                        ),
                        requestFields(
                                fieldWithPath("reviewId").type(NUMBER)
                                        .description("강의 ID"),
                                fieldWithPath("score").type(NUMBER)
                                        .description("후기 점수 / Double 1~5"),
                                fieldWithPath("tags").type(STRING)
                                        .description("후기 태그"),
                                fieldWithPath("content").type(STRING)
                                        .description("후기 내용")
                                        .optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER)
                                        .description("상태 코드"),
                                fieldWithPath("message").type(STRING)
                                        .description("상태 메세지"),
                                fieldWithPath("data.reviewId").type(NUMBER)
                                        .description("후기 ID"),
                                fieldWithPath("data.lectureId").type(NUMBER)
                                        .description("강의 ID"),
                                fieldWithPath("data.userId").type(NUMBER)
                                        .description("유저 ID"),
                                fieldWithPath("data.nickName").type(STRING)
                                        .description("유저 닉네임"),
                                fieldWithPath("data.score").type(NUMBER)
                                        .description("후기 점수 / Double"),
                                fieldWithPath("data.tags").type(STRING)
                                        .description("후기 태그"),
                                fieldWithPath("data.content").type(STRING)
                                        .description("후기 내용 / 없을시 빈 문자열"),
                                fieldWithPath("data.createdDate").type(STRING)
                                        .description("후기 작성 날짜")
                        )
                ));
    }
}
