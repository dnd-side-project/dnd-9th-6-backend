package dnd.project.docs.review;


import dnd.project.docs.RestDocsSupport;
import dnd.project.domain.lecture.response.LectureScopeListReadResponse;
import dnd.project.domain.review.controller.ReviewController;
import dnd.project.domain.review.request.ReviewRequest;
import dnd.project.domain.review.response.ReviewResponse;
import dnd.project.domain.review.service.ReviewService;
import dnd.project.domain.user.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static dnd.project.domain.user.entity.Authority.ROLE_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
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
                new ReviewRequest.Create(1L, 5.0, List.of("빠른 답변", "이해가 잘돼요", "보통이에요"), "강의가 만족스럽습니다!");

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
                                fieldWithPath("tags").type(ARRAY)
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

    @DisplayName("후기 좋아요 및 취소 API")
    @Test
    void toggleLikeReview() throws Exception {
        // given
        given(reviewService.toggleLikeReview(anyLong(), any()))
                .willReturn(
                        ReviewResponse.ToggleLike.builder()
                                .reviewId(1L)
                                .userId(1L)
                                .isCancelled(false)
                                .build()
                );

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/review/like")
                                .header("Authorization", "Bearer AccessToken")
                                .param("reviewId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("toggle-like",
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
                                fieldWithPath("code").type(NUMBER)
                                        .description("상태 코드"),
                                fieldWithPath("message").type(STRING)
                                        .description("상태 메세지"),
                                fieldWithPath("data.reviewId").type(NUMBER)
                                        .description("후기 ID"),
                                fieldWithPath("data.userId").type(NUMBER)
                                        .description("유저 ID"),
                                fieldWithPath("data.isCancelled").type(BOOLEAN)
                                        .description("좋아요 취소 여부 ex) 좋아요 등록시 -> false")
                        )
                ));
    }

    @DisplayName("최근 올라온 후기 조회 API")
    @Test
    void readRecentReview() throws Exception {
        // given
        List<ReviewResponse.ReadDetails> readDetailsList = new ArrayList<>();
        for (long i = 1; i <= 10; i++) {
            ReviewResponse.ReadDetails expectedReadDetails = toEntityReadRecent(i, i, i);
            readDetailsList.add(expectedReadDetails);
        }

        given(reviewService.readRecentReview(any()))
                .willReturn(readDetailsList);

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/review/recent")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("recent-review",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(NUMBER)
                                        .description("상태 코드"),
                                fieldWithPath("message").type(STRING)
                                        .description("상태 메세지"),
                                fieldWithPath("data[].isAddLike").type(BOOLEAN)
                                        .description("후기 좋아요 등록 여부"),
                                fieldWithPath("data[].review.reviewId").type(NUMBER)
                                        .description("후기 ID"),
                                fieldWithPath("data[].review.score").type(NUMBER)
                                        .description("후기 점수"),
                                fieldWithPath("data[].review.content").type(STRING)
                                        .description("후기 내용"),
                                fieldWithPath("data[].review.createdDate").type(STRING)
                                        .description("후기 작성일"),
                                fieldWithPath("data[].review.tags").type(STRING)
                                        .description("후기 태그"),
                                fieldWithPath("data[].review.likes").type(NUMBER)
                                        .description("후기 좋아요 수"),

                                fieldWithPath("data[].lecture.lectureId").type(NUMBER)
                                        .description("강의 ID"),
                                fieldWithPath("data[].lecture.mainCategory").type(STRING)
                                        .description("강의 메인 카테고리"),
                                fieldWithPath("data[].lecture.title").type(STRING)
                                        .description("강의 제목"),
                                fieldWithPath("data[].lecture.name").type(STRING)
                                        .description("강사 이름"),
                                fieldWithPath("data[].lecture.imageUrl").type(STRING)
                                        .description("강의 이미지 URL"),

                                fieldWithPath("data[].user.userId").type(NUMBER)
                                        .description("유저 ID"),
                                fieldWithPath("data[].user.imageUrl").type(STRING)
                                        .description("유저 이미지 URL"),
                                fieldWithPath("data[].user.nickName").type(STRING)
                                        .description("유저 닉네임")
                        )
                ));
    }

    @DisplayName("내 후기 조회 API")
    @Test
    void readMyReviews() throws Exception {
        // given

        // 강의 생성
        ReviewResponse.Lectures lecture1 = ReviewResponse.Lectures.builder()
                .lectureId(1L)
                .title("프로그래밍 입문")
                .imageUrl("https://example.com/lecture1_image.jpg")
                .name("안소희")
                .build();

        ReviewResponse.Lectures lecture2 = ReviewResponse.Lectures.builder()
                .lectureId(2L)
                .title("자료 구조와 알고리즘")
                .imageUrl("https://example.com/lecture2_image.jpg")
                .name("한소희")
                .build();

        ReviewResponse.Lectures lecture3 = ReviewResponse.Lectures.builder()
                .lectureId(3L)
                .title("객체 지향 프로그래밍")
                .name("안유진")
                .imageUrl("https://example.com/lecture3_image.jpg")
                .build();

        // 후기 생성
        ReviewResponse.Reviews review1 = ReviewResponse.Reviews.builder()
                .reviewId(1L)
                .score(4.5)
                .content("프로그래밍 개념에 대한 훌륭한 소개입니다.")
                .createdDate("2023-08-10")
                .tags("빠른 답변, 구성이 알차요, 보통이에요")
                .likes(15)
                .build();

        ReviewResponse.Reviews review2 = ReviewResponse.Reviews.builder()
                .reviewId(2L)
                .score(3.8)
                .content("자료 구조 설명이 좀 더 명확했으면 좋겠습니다.")
                .createdDate("2023-08-11")
                .tags("빠른 답변, 구성이 알차요, 보통이에요")
                .likes(8)
                .build();

        ReviewResponse.Reviews review3 = ReviewResponse.Reviews.builder()
                .reviewId(3L)
                .score(5.0)
                .content("객체 지향 프로그래밍 원리에 대한 훌륭한 강의입니다.")
                .createdDate("2023-08-12")
                .tags("빠른 답변, 구성이 알차요, 보통이에요")
                .likes(20)
                .build();

        // ReadDetails 생성
        ReviewResponse.ReadMyDetails readDetails1 = ReviewResponse.ReadMyDetails.builder()
                .review(review1)
                .lecture(lecture1)
                .build();

        ReviewResponse.ReadMyDetails readDetails2 = ReviewResponse.ReadMyDetails.builder()
                .review(review2)
                .lecture(lecture2)
                .build();

        ReviewResponse.ReadMyDetails readDetails3 = ReviewResponse.ReadMyDetails.builder()
                .review(review3)
                .lecture(lecture3)
                .build();

        given(reviewService.readMyReviews(any()))
                .willReturn(
                        List.of(readDetails1, readDetails2, readDetails3)
                );

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/review")
                                .header("Authorization", "Bearer AccessToken")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("read-my-review",
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
                                fieldWithPath("data[].review.reviewId").type(NUMBER)
                                        .description("후기 ID"),
                                fieldWithPath("data[].review.score").type(NUMBER)
                                        .description("후기 점수"),
                                fieldWithPath("data[].review.content").type(STRING)
                                        .description("후기 내용"),
                                fieldWithPath("data[].review.createdDate").type(STRING)
                                        .description("후기 작성일"),
                                fieldWithPath("data[].review.tags").type(STRING)
                                        .description("후기 태그"),
                                fieldWithPath("data[].review.likes").type(NUMBER)
                                        .description("후기 좋아요 수"),

                                fieldWithPath("data[].lecture.lectureId").type(NUMBER)
                                        .description("강의 ID"),
                                fieldWithPath("data[].lecture.title").type(STRING)
                                        .description("강의 제목"),
                                fieldWithPath("data[].lecture.name").type(STRING)
                                        .description("강사 이름"),
                                fieldWithPath("data[].lecture.imageUrl").type(STRING)
                                        .description("강의 이미지 URL")
                        )
                ));
    }

    @DisplayName("후기 키워드 검색 API")
    @Test
    void readKeywordReview() throws Exception {
        // given
        LectureScopeListReadResponse.DetailReview detailReview1 = new LectureScopeListReadResponse.DetailReview(
                1L, "The RED : 백엔드 에센셜 : 대용량 서비스를 위한 아키텍처 with Redis by 강대명", "https://fastcampus.co.kr/dev_red_kdm",
                "김준환", "2023-08-08", 4.5, "강의가 아주 알차고 재밌습니다. 백엔드 개발 화이팅", "뛰어난 강의력,구성이 알차요,도움이 많이 됐어요"
        );

        LectureScopeListReadResponse.DetailReview detailReview2 = new LectureScopeListReadResponse.DetailReview(
                2L, "MSA 환경의 효율적인 DevOps를 위한 Istio", "https://fastcampus.co.kr/dev_online_istio",
                "천현우", "2023-07-23", 4.5, "강의가 아주 알차고 재밌습니다. 백엔드 개발 화이팅", "뛰어난 강의력,이해가 잘돼요,도움이 많이 됐어요"
        );

        LectureScopeListReadResponse.DetailReview detailReview3 = new LectureScopeListReadResponse.DetailReview(
                3L, "기본적인 양식 요리부터 심화 단계까지 by Erling Haaland", "https://i.namu.wiki/i/U_ewityI8zmTrUbeUAD8IksVCqy8bRFvGev3Dqpp_10Fu-Tr1zidmr6bcdy9d_UEbuGj_uD3OkimkvUh_8t23ehmymYsoNyWwJG_rkubdG2LbwCUdGz3ug-_NjFhQHjz4aXHvuiRGIeiXJRk66CPdw.webp",
                "하예은", "2023-08-23", 5.0, "제가 들어본 요리 강의중에 가장 홀란스러운 요리 강의입니다.", "듣기 좋은 목소리,내용이 자세해요,도움이 많이 됐어요"
        );

        List<LectureScopeListReadResponse.DetailReview> highScoreReviews =
                List.of(detailReview1, detailReview2, detailReview3);

        ReviewRequest.Keyword request = new ReviewRequest.Keyword("커리큘럼과 똑같아요");

        given(reviewService.readKeywordReview(any(ReviewRequest.Keyword.class)))
                .willReturn(highScoreReviews);

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/review/keyword")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("read-review-keyword",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("keyword").type(STRING)
                                        .description("검색 분야")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER)
                                        .description("상태 코드"),
                                fieldWithPath("message").type(STRING)
                                        .description("상태 메세지"),
                                fieldWithPath("data[]").type(ARRAY)
                                        .description("관심 분야 후기 리스트"),
                                fieldWithPath("data[].id").type(NUMBER)
                                        .description("후기 ID"),
                                fieldWithPath("data[].lectureTitle").type(STRING)
                                        .description("강의 제목"),
                                fieldWithPath("data[].imageUrl").type(STRING)
                                        .description("유저 프로필 이미지 URL"),
                                fieldWithPath("data[].userName").type(STRING)
                                        .description("유저 이름"),
                                fieldWithPath("data[].createdDate").type(STRING)
                                        .description("후기 작성 날짜"),
                                fieldWithPath("data[].score").type(NUMBER)
                                        .description("후기 점수"),
                                fieldWithPath("data[].content").type(STRING)
                                        .description("후기 내용"),
                                fieldWithPath("data[].tags").type(STRING)
                                        .description("후기 태그")
                        )
                ));
    }

    // method
    private static ReviewResponse.ReadDetails toEntityReadRecent(Long reviewId, Long lectureId, Long userId) {
        return ReviewResponse.ReadDetails.builder()
                .isAddLike(false)
                .review(ReviewResponse.Reviews.builder()
                        .reviewId(reviewId)
                        .score(4.5)
                        .content("수업이 좋습니다!")
                        .createdDate("2023-08-10")
                        .tags("교육,프로그래밍")
                        .likes(0)
                        .build()
                )
                .lecture(ReviewResponse.Lectures.builder()
                        .lectureId(lectureId)
                        .mainCategory("프로그래밍")
                        .name("안유진")
                        .title("프로그래밍 입문" + lectureId)
                        .imageUrl("https://example.com/programming.jpg")
                        .build()
                )
                .user(ReviewResponse.User.builder()
                        .userId(userId)
                        .imageUrl("https://example.com/user1.jpg")
                        .nickName("사용자" + userId)
                        .build()
                )
                .build();
    }
}
