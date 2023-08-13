package dnd.project.docs.lecture;

import dnd.project.docs.RestDocsSupport;
import dnd.project.domain.lecture.controller.LectureController;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.response.LectureListReadResponse;
import dnd.project.domain.lecture.response.LectureScopeListReadResponse;
import dnd.project.domain.lecture.service.LectureService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.print.DocFlavor;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LectureControllerDocsTest extends RestDocsSupport {

    private final LectureService lectureService = mock(LectureService.class);

    static final List<Lecture> LECTURES = List.of(
            Lecture.builder()
                    .id(1L)
                    .title("스프링 부트 - 핵심 원리와 활용")
                    .source("Inflearn")
                    .url("url")
                    .price("99000")
                    .name("김영한")
                    .mainCategory("프로그래밍")
                    .subCategory("웹")
                    .keywords("스프링,스프링부트")
                    .content("실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다.")
                    .imageUrl("url")
                    .build(),

            Lecture.builder()
                    .id(2L)
                    .title("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술")
                    .source("Inflearn")
                    .url("url")
                    .price("99,000")
                    .name("김영한")
                    .mainCategory("프로그래밍")
                    .subCategory("웹")
                    .keywords("스프링,스프링MVC")
                    .content("웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다.")
                    .imageUrl("url")
                    .build(),

            Lecture.builder()
                    .id(3L)
                    .title("스프링 DB 2편 - 데이터 접근 활용 기술")
                    .source("Inflearn")
                    .url("url")
                    .price("99,000")
                    .name("김영한")
                    .mainCategory("프로그래밍")
                    .subCategory("웹")
                    .keywords("스프링,DB")
                    .content("백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다.")
                    .imageUrl("url")
                    .build(),

            Lecture.builder()
                    .id(4L)
                    .title("배달앱 클론코딩 [with React Native]")
                    .source("Inflearn")
                    .url("url")
                    .price("71,500")
                    .name("조현영")
                    .mainCategory("프로그래밍")
                    .subCategory("앱")
                    .keywords("리액트 네이티브")
                    .content("리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다.")
                    .imageUrl("url")
                    .build(),

            Lecture.builder()
                    .id(5L)
                    .title("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지")
                    .source("Inflearn")
                    .url("url")
                    .price("205,700")
                    .name("앨런(Allen)")
                    .mainCategory("프로그래밍")
                    .subCategory("앱")
                    .keywords("iOS")
                    .content("탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초")
                    .imageUrl("url")
                    .build()
    );

    @Override
    protected Object initController() {
        return new LectureController(lectureService);
    }

    @DisplayName("강의 검색 API")
    @Test
    void addBookmark() throws Exception {
        // given
        given(lectureService.getLectures(any(), any(), any(), any(), any(), any()))
                .willReturn(
                        LectureListReadResponse.of(
                                10,
                                1,
                                10,
                                100L,
                                LECTURES
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
                        queryParameters(
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
                                                "review,desc(리뷰수 내림차순)")
                        ),
                        responseFields(
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
                                        .description("강의 썸네일 URL")
                        )
                ));
    }

    @DisplayName("scope 페이지 추천 강의 조회 API")
    @Test
    void getScopeLectures() throws Exception {
        // given
        // 후기 높은 리뷰 생성
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

        // 추천 강의 생성
        LectureScopeListReadResponse.DetailLecture detailLecture1 = new LectureScopeListReadResponse.DetailLecture(
                1L, "https://fastcampus.co.kr/dev_online_linux", "리눅스 실전 정복 올인원 패키지 Online.", "박수현,원규연"
        );

        LectureScopeListReadResponse.DetailLecture detailLecture2 = new LectureScopeListReadResponse.DetailLecture(
                2L, "https://fastcampus.co.kr/data_online_msignature", "초격차 패키지 : 50개 프로젝트로 완벽하게 끝내는 머신러닝 SIGNATURE", "박지환,안건이,박창용,김원균"
        );

        LectureScopeListReadResponse.DetailLecture detailLecture3 = new LectureScopeListReadResponse.DetailLecture(
                3L, "https://i.namu.wiki/i/Z6zyrokfaBgMqSMZdsGZXZ2u8CeM9ZOuyIgihmxorIVVNicpOtOcsF0P-LyBmH3pMbApRXnGQBAkvAN7JJQkU_GEmALHdP7l1R7oTHYp6MZKhF8aZ5TDc6kMSUB2Y60aZDSUnIcHwZzT4C5N7XkowQ.webp", "영국 요리가 맛없다는 편견은 그만! 영국 왕실 특별 대접 레시피 패키지", "케빈 데브라위너"
        );

        List<LectureScopeListReadResponse.DetailLecture> bestLectures = List.of(detailLecture1, detailLecture2, detailLecture3);

        given(lectureService.getScopeLectures(any()))
                .willReturn(
                        LectureScopeListReadResponse.builder()
                                .isAnonymous(false)
                                .userName("클래스코프")
                                .interests("요리,프로그래밍")
                                .highScoreReviews(highScoreReviews)
                                .bestLectures(bestLectures)
                                .build()
                );

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/lectures/scope")
                                .header("Authorization", "Bearer AccessToken")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("scope-main",
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("발급된 JWT AccessToken / NULL 허용")
                        ),
                        responseFields(
                                fieldWithPath("code").type(NUMBER)
                                        .description("상태 코드"),
                                fieldWithPath("message").type(STRING)
                                        .description("상태 메세지"),
                                fieldWithPath("data.isAnonymous").type(BOOLEAN)
                                        .description("비로그인 유저 여부"),
                                fieldWithPath("data.userName").type(STRING)
                                        .description("유저 네임 / 비로그인 값 : 'anonymous'"),
                                fieldWithPath("data.interests").type(STRING)
                                        .description("유저 관심분야 / 비로그인 값 : 'anonymous'"),
                                fieldWithPath("data.highScoreReviews[]").type(ARRAY)
                                        .description("별점 높은 수강 후기 리스트"),
                                fieldWithPath("data.highScoreReviews[].id").type(NUMBER)
                                        .description("후기 ID"),
                                fieldWithPath("data.highScoreReviews[].lectureTitle").type(STRING)
                                        .description("강의 제목"),
                                fieldWithPath("data.highScoreReviews[].imageUrl").type(STRING)
                                        .description("유저 프로필 이미지 URL"),
                                fieldWithPath("data.highScoreReviews[].userName").type(STRING)
                                        .description("유저 이름"),
                                fieldWithPath("data.highScoreReviews[].createdDate").type(STRING)
                                        .description("후기 작성 날짜"),
                                fieldWithPath("data.highScoreReviews[].score").type(NUMBER)
                                        .description("후기 점수"),
                                fieldWithPath("data.highScoreReviews[].content").type(STRING)
                                        .description("후기 내용"),
                                fieldWithPath("data.highScoreReviews[].tags").type(STRING)
                                        .description("후기 태그"),
                                fieldWithPath("data.bestLectures[]").type(ARRAY)
                                        .description("강의력 좋은 강의 리스트"),
                                fieldWithPath("data.bestLectures[].id").type(NUMBER)
                                        .description("강의 Id"),
                                fieldWithPath("data.bestLectures[].imageUrl").type(STRING)
                                        .description("강의 이미지 URL"),
                                fieldWithPath("data.bestLectures[].title").type(STRING)
                                        .description("강의 제목"),
                                fieldWithPath("data.bestLectures[].name").type(STRING)
                                        .description("강사 이름")
                        )
                ));
    }
}
