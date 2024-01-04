package dnd.project.docs.lecture;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import dnd.project.docs.RestDocsSupport;
import dnd.project.domain.lecture.controller.LectureController;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.response.*;
import dnd.project.domain.lecture.service.LectureService;
import dnd.project.domain.review.entity.ReviewTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.Schema.schema;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LectureControllerDocsTest extends RestDocsSupport {

    private final LectureService lectureService = mock(LectureService.class);

    @Override
    protected Object initController() {
        return new LectureController(lectureService);
    }

    @DisplayName("강의 검색 API")
    @Test
    void getLectures() throws Exception {
        // given
        List<LectureListReadResponse.LectureInfo> totalLectures = List.of(
                LectureListReadResponse.LectureInfo.of(
                        getLecture(1L,
                                "스프링 부트 - 핵심 원리와 활용",
                                "99,000",
                                "김영한",
                                "프로그래밍",
                                "웹",
                                "스프링,스프링부트",
                                "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                        10L,
                        5L,
                        5.0,
                        List.of(new TagGroup("강사에 대해", List.of(new TagGroup.Tag("빠른 답변", 5), new TagGroup.Tag("듣기 좋은 목소리", 3)))),
                        List.of(new LectureListReadResponse.LectureInfo.ReviewInfo(1L, "user1", "빠른 답변,듣기 좋은 목소리", "좋아요", LocalDateTime.of(2024, 1, 1, 0, 0), 5.0, 5L))),
                LectureListReadResponse.LectureInfo.of(getLecture(2L,
                                "스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술",
                                "99,000",
                                "김영한",
                                "프로그래밍",
                                "웹",
                                "스프링,스프링MVC",
                                "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                        5L,
                        1L,
                        5.0,
                        List.of(new TagGroup("강사에 대해", List.of(new TagGroup.Tag("빠른 답변", 5), new TagGroup.Tag("듣기 좋은 목소리", 3)))),
                        List.of(new LectureListReadResponse.LectureInfo.ReviewInfo(1L, "user1", "빠른 답변,듣기 좋은 목소리", "좋아요", LocalDateTime.of(2024, 1, 1, 0, 0), 5.0, 5L))),
                LectureListReadResponse.LectureInfo.of(getLecture(3L,
                                "스프링 DB 2편 - 데이터 접근 활용 기술",
                                "99000",
                                "김영한",
                                "프로그래밍",
                                "웹",
                                "스프링,DB",
                                "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                        3L,
                        4L,
                        5.0,
                        List.of(new TagGroup("강사에 대해", List.of(new TagGroup.Tag("빠른 답변", 5), new TagGroup.Tag("듣기 좋은 목소리", 3)))),
                        List.of(new LectureListReadResponse.LectureInfo.ReviewInfo(1L, "user1", "빠른 답변,듣기 좋은 목소리", "좋아요", LocalDateTime.of(2024, 1, 1, 0, 0), 5.0, 5L))),
                LectureListReadResponse.LectureInfo.of(getLecture(4L,
                                "배달앱 클론코딩 [with React Native]",
                                "71,500",
                                "조현영",
                                "프로그래밍",
                                "웹",
                                "리액트 네이티브",
                                "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                        4L,
                        20L,
                        5.0,
                        List.of(new TagGroup("강사에 대해", List.of(new TagGroup.Tag("빠른 답변", 5), new TagGroup.Tag("듣기 좋은 목소리", 3)))),
                        List.of(new LectureListReadResponse.LectureInfo.ReviewInfo(1L, "user1", "빠른 답변,듣기 좋은 목소리", "좋아요", LocalDateTime.of(2024, 1, 1, 0, 0), 5.0, 5L))),
                LectureListReadResponse.LectureInfo.of(getLecture(5L,
                                "앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지",
                                "205,700",
                                "앨런(Allen)",
                                "프로그래밍",
                                "웹",
                                "iOS",
                                "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"),
                        1L,
                        11L,
                        5.0,
                        List.of(new TagGroup("강사에 대해", List.of(new TagGroup.Tag("빠른 답변", 5), new TagGroup.Tag("듣기 좋은 목소리", 3)))),
                        List.of(new LectureListReadResponse.LectureInfo.ReviewInfo(1L, "user1", "빠른 답변,듣기 좋은 목소리", "좋아요", LocalDateTime.of(2024, 1, 1, 0, 0), 5.0, 5L))));

        given(lectureService.getLectures(any(), any(), any(), any(), any(), any()))
                .willReturn(
                        LectureListReadResponse.of(
                                10,
                                1,
                                10,
                                100L,
                                totalLectures
                        ));

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/lectures")
                                .param("mainCategoryId", "1")
                                .param("subCategoryId", "1")
                                .param("searchKeyword", "web")
                                .param("page", "0")
                                .param("size", "10")
                                .param("sort", "price,asc")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-lectures",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("강의 API")
                                .summary("강의 검색 API")
                                .queryParameters(
                                        parameterWithName("mainCategoryId")
                                                .description("메인 카테고리 ID"),
                                        parameterWithName("subCategoryId")
                                                .description("서브 카테고리 ID"),
                                        parameterWithName("searchKeyword")
                                                .description("검색어"),
                                        parameterWithName("page")
                                                .description("페이지 번호"),
                                        parameterWithName("size")
                                                .description("한 페이지의 데이터 개수"),
                                        parameterWithName("sort")
                                                .description("정렬 파라미터,오름차순 또는 내림차순 +\n" +
                                                        "ex) +\n" +
                                                        "price,asc(가격 오름차순) +\n" +
                                                        "price,desc(가격 내림차순) +\n" +
                                                        "title,asc(강의명 오름차순) +\n" +
                                                        "title,desc(강의명 내림차순) +\n" +
                                                        "name,asc(강사명 오름차순) +\n" +
                                                        "name,desc(강사명 내림차순) +\n" +
                                                        "bookmark,asc(북마크수 오름차순) +\n" +
                                                        "bookmark,desc(북마크수 내림차순) +\n" +
                                                        "review,asc(리뷰수 오름차순) +\n" +
                                                        "review,desc(리뷰수 내림차순)"))
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("상태 메세지"),
                                        fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER)
                                                .description("검색 페이지 수"),
                                        fieldWithPath("data.pageNumber").type(JsonFieldType.NUMBER)
                                                .description("현재 페이지 번호"),
                                        fieldWithPath("data.pageSize").type(JsonFieldType.NUMBER)
                                                .description("한 페이지의 데이터 개수"),
                                        fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER)
                                                .description("검색 데이터 개수"),
                                        fieldWithPath("data.lectures[]").type(JsonFieldType.ARRAY)
                                                .description("강의 목록"),
                                        fieldWithPath("data.lectures[].id").type(JsonFieldType.NUMBER)
                                                .description("강의 ID"),
                                        fieldWithPath("data.lectures[].title").type(JsonFieldType.STRING)
                                                .description("강의 제목"),
                                        fieldWithPath("data.lectures[].source").type(JsonFieldType.STRING)
                                                .description("강의 플랫폼"),
                                        fieldWithPath("data.lectures[].url").type(JsonFieldType.STRING)
                                                .description("강의 URL"),
                                        fieldWithPath("data.lectures[].price").type(JsonFieldType.STRING)
                                                .description("강의 가격"),
                                        fieldWithPath("data.lectures[].name").type(JsonFieldType.STRING)
                                                .description("강사 이름"),
                                        fieldWithPath("data.lectures[].mainCategory").type(JsonFieldType.STRING)
                                                .description("강의 메인 카테고리"),
                                        fieldWithPath("data.lectures[].subCategory").type(JsonFieldType.STRING)
                                                .description("강의 서브 카테고리"),
                                        fieldWithPath("data.lectures[].imageUrl").type(JsonFieldType.STRING)
                                                .description("강의 썸네일 URL"),
                                        fieldWithPath("data.lectures[].reviewCount").type(JsonFieldType.NUMBER)
                                                .description("강의 리뷰 수"),
                                        fieldWithPath("data.lectures[].bookmarkCount").type(JsonFieldType.NUMBER)
                                                .description("강의 북마크 수"),
                                        fieldWithPath("data.lectures[].averageScore").type(JsonFieldType.NUMBER)
                                                .description("강의 평점"),
                                        fieldWithPath("data.lectures[].tagGroups[]").type(JsonFieldType.ARRAY)
                                                .description("강의 태그 그룹 목록"),
                                        fieldWithPath("data.lectures[].tagGroups[].name").type(JsonFieldType.STRING)
                                                .description("강의 태그 타입 이름"),
                                        fieldWithPath("data.lectures[].tagGroups[].tags[]").type(JsonFieldType.ARRAY)
                                                .description("강의 태그 목록"),
                                        fieldWithPath("data.lectures[].tagGroups[].tags[].name").type(JsonFieldType.STRING)
                                                .description("강의 태그 이름"),
                                        fieldWithPath("data.lectures[].tagGroups[].tags[].count").type(JsonFieldType.NUMBER)
                                                .description("강의 태그 개수"),
                                        fieldWithPath("data.lectures[].reviews[]").type(JsonFieldType.ARRAY)
                                                .description("후기 목록"),
                                        fieldWithPath("data.lectures[].reviews[].id").type(JsonFieldType.NUMBER)
                                                .description("후기 ID"),
                                        fieldWithPath("data.lectures[].reviews[].nickname").type(JsonFieldType.STRING)
                                                .description("후기 작성자 닉네임"),
                                        fieldWithPath("data.lectures[].reviews[].tags[]").type(JsonFieldType.ARRAY)
                                                .description("후기 태그 목록"),
                                        fieldWithPath("data.lectures[].reviews[].content").type(JsonFieldType.STRING)
                                                .description("후기 내용"),
                                        fieldWithPath("data.lectures[].reviews[].createdDate").type(JsonFieldType.STRING)
                                                .description("후기 생성일"),
                                        fieldWithPath("data.lectures[].reviews[].score").type(JsonFieldType.NUMBER)
                                                .description("후기 점수"),
                                        fieldWithPath("data.lectures[].reviews[].likeCount").type(JsonFieldType.NUMBER)
                                                .description("후기 추천수"))
                                .responseSchema(schema("LectureListReadResponse"))
                                .build())));
    }

    @DisplayName("강의 상세 조회 API")
    @Test
    void getLecture() throws Exception {

        // Given
        Lecture lecture = getLecture(1L,
                "스프링 부트 - 핵심 원리와 활용",
                "99,000",
                "김영한",
                "프로그래밍",
                "웹",
                "스프링,스프링부트",
                "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다.");

        // tagType 리스트
        Set<String> tagTypes = Arrays.stream(ReviewTag.values())
                .map(ReviewTag::getType)
                .collect(Collectors.toSet());

        List<TagGroup> tagGroups = new ArrayList<>();
        for (String tagType : tagTypes) {
            List<String> tagNames = Arrays.stream(ReviewTag.values())
                    .filter(tag -> tag.getType().equals(tagType))
                    .map(ReviewTag::getName)
                    .toList();

            List<TagGroup.Tag> tagList = new ArrayList<>();
            for (int i = 0; i < tagNames.size(); i++) {
                tagList.add(new TagGroup.Tag(tagNames.get(i), i));
            }
            tagGroups.add(new TagGroup(tagType, tagList));
        }

        given(lectureService.getLecture(any()))
                .willReturn(LectureReadResponse.of(
                        lecture,
                        3L,
                        4.5,
                        tagGroups
                ));

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/lectures/{id}", 1)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-lecture",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("강의 API")
                                .summary("강의 상세 조회 API")
                                .pathParameters(parameterWithName("id")
                                        .description("강의 ID"))
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("상태 메세지"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                                                .description("강의 ID"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING)
                                                .description("강의 제목"),
                                        fieldWithPath("data.source").type(JsonFieldType.STRING)
                                                .description("강의 플랫폼"),
                                        fieldWithPath("data.url").type(JsonFieldType.STRING)
                                                .description("강의 URL"),
                                        fieldWithPath("data.price").type(JsonFieldType.STRING)
                                                .description("강의 가격"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING)
                                                .description("강사 이름"),
                                        fieldWithPath("data.mainCategory").type(JsonFieldType.STRING)
                                                .description("강의 메인 카테고리"),
                                        fieldWithPath("data.subCategory").type(JsonFieldType.STRING)
                                                .description("강의 서브 카테고리"),
                                        fieldWithPath("data.imageUrl").type(JsonFieldType.STRING)
                                                .description("강의 썸네일 URL"),
                                        fieldWithPath("data.reviewCount").type(JsonFieldType.NUMBER)
                                                .description("리뷰 전체 개수"),
                                        fieldWithPath("data.averageScore").type(JsonFieldType.NUMBER)
                                                .description("강의 평점"),
                                        fieldWithPath("data.tagGroups[]").type(JsonFieldType.ARRAY)
                                                .description("강의 태그 그룹 목록"),
                                        fieldWithPath("data.tagGroups[].name").type(JsonFieldType.STRING)
                                                .description("강의 태그 타입 이름"),
                                        fieldWithPath("data.tagGroups[].tags[]").type(JsonFieldType.ARRAY)
                                                .description("강의 태그 목록"),
                                        fieldWithPath("data.tagGroups[].tags[].name").type(JsonFieldType.STRING)
                                                .description("강의 태그 이름"),
                                        fieldWithPath("data.tagGroups[].tags[].count").type(JsonFieldType.NUMBER)
                                                .description("강의 태그 개수"))
                                .responseSchema(schema("LectureReadResponse"))
                                .build())));
    }

    @DisplayName("강의 상세 리뷰 조회 API")
    @Test
    void getLectureReviews() throws Exception {

        // given
        given(lectureService.getLectureReviews(any(), any(), any(), any(), any()))
                .willReturn(LectureReviewListReadResponse.of(
                        10,
                        1,
                        5,
                        50L,
                        List.of(
                                new LectureReviewListReadResponse.ReviewInfo(1L,
                                        "user1", "뛰어난 강의력, 커리큘럼과 똑같아요, 보통이에요",
                                        "스프링 개발 추천 강의1",
                                        LocalDateTime.of(2023, 8, 14, 13, 0),
                                        4.5,
                                        5L),
                                new LectureReviewListReadResponse.ReviewInfo(2L,
                                        "user2",
                                        "듣기 좋은 목소리,내용이 자세해요",
                                        "스프링 개발 추천 강의1",
                                        LocalDateTime.of(2023, 8, 14, 13, 0),
                                        5.0,
                                        2L),
                                new LectureReviewListReadResponse.ReviewInfo(3L,
                                        "user3",
                                        "듣기 좋은 목소리,도움이 많이 됐어요",
                                        "스프링 개발 추천 강의1",
                                        LocalDateTime.of(2023, 8, 14, 13, 0),
                                        3.5,
                                        1L),
                                new LectureReviewListReadResponse.ReviewInfo(4L,
                                        "user4",
                                        "뛰어난 강의력,구성이 알차요,도움이 많이 됐어요",
                                        "스프링 개발 추천 강의1",
                                        LocalDateTime.of(2023, 8, 14, 13, 0),
                                        4.5,
                                        4L),
                                new LectureReviewListReadResponse.ReviewInfo(5L,
                                        "user5",
                                        "뛰어난 강의력,이해가 잘돼요,보통이에요",
                                        "스프링 개발 추천 강의1",
                                        LocalDateTime.of(2023, 8, 14, 13, 0),
                                        4.0,
                                        3L))));

        // when & then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/lectures/{id}/reviews", 1)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-lecture-reviews",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("강의 API")
                                .summary("강의 상세 리뷰 조회 API")
                                .pathParameters(parameterWithName("id")
                                        .description("강의 ID"))
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("상태 메세지"),
                                        fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER)
                                                .description("검색 페이지 수"),
                                        fieldWithPath("data.pageNumber").type(JsonFieldType.NUMBER)
                                                .description("현재 페이지 번호"),
                                        fieldWithPath("data.pageSize").type(JsonFieldType.NUMBER)
                                                .description("한 페이지의 데이터 개수"),
                                        fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER)
                                                .description("검색 데이터 개수"),
                                        fieldWithPath("data.reviews[]").type(JsonFieldType.ARRAY)
                                                .description("후기 목록"),
                                        fieldWithPath("data.reviews[].id").type(JsonFieldType.NUMBER)
                                                .description("후기 ID"),
                                        fieldWithPath("data.reviews[].nickname").type(JsonFieldType.STRING)
                                                .description("후기 작성자 닉네임"),
                                        fieldWithPath("data.reviews[].tags[]").type(JsonFieldType.ARRAY)
                                                .description("후기 태그 목록"),
                                        fieldWithPath("data.reviews[].content").type(JsonFieldType.STRING)
                                                .description("후기 내용"),
                                        fieldWithPath("data.reviews[].createdDate").type(JsonFieldType.STRING)
                                                .description("후기 생성일"),
                                        fieldWithPath("data.reviews[].score").type(JsonFieldType.NUMBER)
                                                .description("후기 점수"),
                                        fieldWithPath("data.reviews[].likeCount").type(JsonFieldType.NUMBER)
                                                .description("후기 추천수"))
                                .responseSchema(schema("LectureReviewListReadResponse"))
                                .build())));
    }

    @DisplayName("별점 높은 수강 후기들 API")
    @Test
    void getScopeReviewsScore() throws Exception {
        // given
        // 후기 높은 리뷰 생성
        LectureScopeListReadResponse.DetailReview detailReview1 = new LectureScopeListReadResponse.DetailReview(
                1L, "The RED : 백엔드 에센셜 : 대용량 서비스를 위한 아키텍처 with Redis by 강대명", "김준환",
                4.5, "강의가 아주 알차고 재밌습니다. 백엔드 개발 화이팅", "뛰어난 강의력,구성이 알차요,도움이 많이 됐어요", "coloso", LocalDateTime.of(2023, 8, 8, 12, 13, 30)
        );

        LectureScopeListReadResponse.DetailReview detailReview2 = new LectureScopeListReadResponse.DetailReview(
                2L, "MSA 환경의 효율적인 DevOps를 위한 Istio", "천현우",
                4.5, "강의가 아주 알차고 재밌습니다. 백엔드 개발 화이팅", "뛰어난 강의력,이해가 잘돼요,도움이 많이 됐어요", "coloso", LocalDateTime.of(2023, 8, 13, 12, 13, 30)
        );

        LectureScopeListReadResponse.DetailReview detailReview3 = new LectureScopeListReadResponse.DetailReview(
                3L, "기본적인 양식 요리부터 심화 단계까지 by Erling Haaland", "하예은", 5.0,
                "제가 들어본 요리 강의중에 가장 홀란스러운 요리 강의입니다.", "듣기 좋은 목소리,내용이 자세해요,도움이 많이 됐어요", "coloso", LocalDateTime.of(2023, 8, 25, 12, 13, 30)
        );

        List<LectureScopeListReadResponse.DetailReview> highScoreReviews =
                List.of(detailReview1, detailReview2, detailReview3);

        given(lectureService.getScopeReviewsScore(any()))
                .willReturn(highScoreReviews);

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/lectures/scope/reviews")
                                .header(AUTHORIZATION, "Bearer {token}")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("scope-review-score",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("스코프 API")
                                .summary("별점 높은 수강 후기들 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization")
                                                .description("Swagger 요청시 해당 입력칸이 아닌 우측 상단 자물쇠 " +
                                                        "또는 Authorize 버튼을 이용해 토큰을 넣어주세요"))
                                .responseFields(
                                        fieldWithPath("code").type(NUMBER)
                                                .description("상태 코드"),
                                        fieldWithPath("message").type(STRING)
                                                .description("상태 메세지"),
                                        fieldWithPath("data[]").type(ARRAY)
                                                .description("별점 높은 수강 후기 리스트"),
                                        fieldWithPath("data[].id").type(NUMBER)
                                                .description("후기 ID"),
                                        fieldWithPath("data[].lectureTitle").type(STRING)
                                                .description("강의 제목"),
                                        fieldWithPath("data[].userName").type(STRING)
                                                .description("유저 이름"),
                                        fieldWithPath("data[].createdDate").type(STRING)
                                                .description("후기 작성 날짜"),
                                        fieldWithPath("data[].score").type(NUMBER)
                                                .description("후기 점수"),
                                        fieldWithPath("data[].content").type(STRING)
                                                .description("후기 내용"),
                                        fieldWithPath("data[].tags").type(STRING)
                                                .description("후기 태그"),
                                        fieldWithPath("data[].source").type(STRING)
                                                .description("강의 플랫폼"))
                                .responseSchema(schema("LectureScopeListReadResponse.DetailReview"))
                                .build())));
    }

    @DisplayName("강의력 좋은 강의 조회 API")
    @Test
    void getScopeLecturesBest() throws Exception {
        // given
        // 추천 강의 생성
        LectureScopeListReadResponse.DetailLecture detailLecture1 = new LectureScopeListReadResponse.DetailLecture(
                1L, "fastcampus", "https://fastcampus.co.kr/dev_online_linux", "리눅스 실전 정복 올인원 패키지 Online.", "박수현,원규연", "https://fastcampus.co.kr/dev_online_linux"
        );

        LectureScopeListReadResponse.DetailLecture detailLecture2 = new LectureScopeListReadResponse.DetailLecture(
                2L, "fastcampus", "https://fastcampus.co.kr/data_online_msignature", "초격차 패키지 : 50개 프로젝트로 완벽하게 끝내는 머신러닝 SIGNATURE", "박지환,안건이,박창용,김원균", "https://fastcampus.co.kr/data_online_msignature"
        );

        LectureScopeListReadResponse.DetailLecture detailLecture3 = new LectureScopeListReadResponse.DetailLecture(
                3L, "coloso", "https://i.namu.wiki/i/Z6zyrokfaBgMqSMZdsGZXZ2u8CeM9ZOuyIgihmxorIVVNicpOtOcsF0P-LyBmH3pMbApRXnGQBAkvAN7JJQkU_GEmALHdP7l1R7oTHYp6MZKhF8aZ5TDc6kMSUB2Y60aZDSUnIcHwZzT4C5N7XkowQ.webp", "영국 요리가 맛없다는 편견은 그만! 영국 왕실 특별 대접 레시피 패키지", "케빈 데브라위너", "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA4MDZfODYg%2FMDAxNjkxMjg2MDczMDY0.U5o5JYOpggrnIq-0Ti89rsiDLAD0MMhhcEHYlpDSDlIg.OJ2J-_z3gdSDyLx3fP4Zb8HkIFRni0aBnqN_VoubXywg.PNG.haveitall7786%2Fimage.png&type=sc960_832"
        );

        List<LectureScopeListReadResponse.DetailLecture> bestLectures = List.of(detailLecture1, detailLecture2, detailLecture3);

        given(lectureService.getScopeLecturesBest(any()))
                .willReturn(bestLectures);

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/lectures/scope/lectures")
                                .header(AUTHORIZATION, "Bearer {token}")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("scope-review-lecture",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("스코프 API")
                                .summary("강의력 좋은 강의 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization")
                                                .description("Swagger 요청시 해당 입력칸이 아닌 우측 상단 자물쇠 " +
                                                        "또는 Authorize 버튼을 이용해 토큰을 넣어주세요"))
                                .responseFields(
                                        fieldWithPath("code").type(NUMBER)
                                                .description("상태 코드"),
                                        fieldWithPath("message").type(STRING)
                                                .description("상태 메세지"),
                                        fieldWithPath("data[]").type(ARRAY)
                                                .description("강의력 좋은 강의 리스트"),
                                        fieldWithPath("data[].id").type(NUMBER)
                                                .description("강의 Id"),
                                        fieldWithPath("data[].source").type(STRING)
                                                .description("강의 플랫폼"),
                                        fieldWithPath("data[].imageUrl").type(STRING)
                                                .description("강의 이미지 URL"),
                                        fieldWithPath("data[].title").type(STRING)
                                                .description("강의 제목"),
                                        fieldWithPath("data[].name").type(STRING)
                                                .description("강사 이름"),
                                        fieldWithPath("data[].url").type(STRING)
                                                .description("강의 URL"))
                                .responseSchema(schema("LectureScopeListReadResponse.DetailLecture"))
                                .build())));
    }

    private static Lecture getLecture(Long id,
                                      String title,
                                      String price,
                                      String name,
                                      String mainCategory,
                                      String subCategory,
                                      String keywords,
                                      String content) {
        return Lecture.builder()
                .id(id)
                .title(title)
                .source("출처")
                .url("URL")
                .price(price)
                .name(name)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .keywords(keywords)
                .content(content)
                .imageUrl("이미지 URL")
                .build();
    }
}
