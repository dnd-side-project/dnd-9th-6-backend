package dnd.project.domain.lecture.controller;

import dnd.project.domain.ControllerTestSupport;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.response.LectureListReadResponse;
import dnd.project.domain.lecture.response.LectureReadResponse;
import dnd.project.domain.lecture.response.LectureReviewListReadResponse;
import dnd.project.domain.review.entity.ReviewTag;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LectureControllerTest extends ControllerTestSupport {

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
                        5L),
                LectureListReadResponse.LectureInfo.of(getLecture(2L,
                                "스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술",
                                "99,000",
                                "김영한",
                                "프로그래밍",
                                "웹",
                                "스프링,스프링MVC",
                                "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                        5L,
                        1L),
                LectureListReadResponse.LectureInfo.of(getLecture(3L,
                                "스프링 DB 2편 - 데이터 접근 활용 기술",
                                "99000",
                                "김영한",
                                "프로그래밍",
                                "웹",
                                "스프링,DB",
                                "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                        3L,
                        4L),
                LectureListReadResponse.LectureInfo.of(getLecture(4L,
                                "배달앱 클론코딩 [with React Native]",
                                "71,500",
                                "조현영",
                                "프로그래밍",
                                "웹",
                                "리액트 네이티브",
                                "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                        4L,
                        20L),
                LectureListReadResponse.LectureInfo.of(getLecture(5L,
                        "앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지",
                        "205,700",
                        "앨런(Allen)",
                        "프로그래밍",
                        "웹",
                        "iOS",
                        "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"),
                        1L,
                        11L));

        given(lectureService.getLectures(any(), any(), any(), any(), any(), any()))
                .willReturn(
                        LectureListReadResponse.of(
                                10,
                                1,
                                10,
                                100L,
                                totalLectures
                        ));

        // when & then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/lectures")
                                .param("mainCategoryId", "1")
                                .param("subCategoryId", "1")
                                .param("page", "0")
                                .param("size", "10")
                                .param("sort", "price,asc")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalPages").value(10))
                .andExpect(jsonPath("$.data.pageNumber").value(1))
                .andExpect(jsonPath("$.data.pageSize").value(10))
                .andExpect(jsonPath("$.data.totalElements").value(100))
                .andExpect(jsonPath("$.data.lectures.size()").value(5))
                .andExpect(jsonPath("$.data.lectures[*].id", Matchers.contains(1, 2, 3, 4, 5)))
                .andExpect(jsonPath("$.data.lectures[*].title", Matchers.contains(
                        "스프링 부트 - 핵심 원리와 활용",
                        "스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술",
                        "스프링 DB 2편 - 데이터 접근 활용 기술",
                        "배달앱 클론코딩 [with React Native]",
                        "앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지")))
                .andExpect(jsonPath("$.data.lectures[*].reviewCount", Matchers.contains(10, 5, 3, 4, 1)))
                .andExpect(jsonPath("$.data.lectures[*].bookmarkCount", Matchers.contains(5, 1, 4, 20, 11)));
    }

    @DisplayName("강의 상세 조회 API")
    @Test
    void getLecture() throws Exception {
        // given
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

        List<LectureReadResponse.TagGroup> tagGroups = new ArrayList<>();
        for (String tagType : tagTypes) {
            List<String> tagNames = Arrays.stream(ReviewTag.values())
                    .filter(tag -> tag.getType().equals(tagType))
                    .map(ReviewTag::getName)
                    .toList();

            List<LectureReadResponse.TagGroup.Tag> tagList = new ArrayList<>();
            for (int i = 0; i < tagNames.size(); i++) {
                tagList.add(new LectureReadResponse.TagGroup.Tag(tagNames.get(i), i));
            }
            tagGroups.add(new LectureReadResponse.TagGroup(tagType, tagList));
        }

        given(lectureService.getLecture(any()))
                .willReturn(LectureReadResponse.of(
                        lecture,
                        3L,
                        4.5,
                        tagGroups
                ));

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/lectures/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(lecture.getId()))
                .andExpect(jsonPath("$.data.title").value(lecture.getTitle()))
                .andExpect(jsonPath("$.data.source").value(lecture.getSource()))
                .andExpect(jsonPath("$.data.url").value(lecture.getUrl()))
                .andExpect(jsonPath("$.data.price").value(lecture.getPrice()))
                .andExpect(jsonPath("$.data.mainCategory").value(lecture.getMainCategory()))
                .andExpect(jsonPath("$.data.subCategory").value(lecture.getSubCategory()))
                .andExpect(jsonPath("$.data.imageUrl").value(lecture.getImageUrl()))
                .andExpect(jsonPath("$.data.reviewCount").value(3))
                .andExpect(jsonPath("$.data.averageScore").value(4.5))
                .andExpect(jsonPath("$.data.tagGroups[*].name", Matchers.contains("강사에 대해", "강의내용에 대해", "강의후, 느끼는 변화")))
                .andExpect(jsonPath("$.data.tagGroups[*].tags[*].name", Matchers.contains(
                        "빠른 답변",
                        "듣기 좋은 목소리",
                        "뛰어난 강의력",
                        "매우 적극적",
                        "커리큘럼과 똑같아요",
                        "구성이 알차요",
                        "내용이 자세해요",
                        "이해가 잘돼요",
                        "도움이 많이 됐어요",
                        "보통이에요",
                        "도움이 안되었어요"
                )));
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
        mockMvc.perform(MockMvcRequestBuilders.get("/lectures/{id}/reviews", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalPages").value(10))
                .andExpect(jsonPath("$.data.pageNumber").value(1))
                .andExpect(jsonPath("$.data.pageSize").value(5))
                .andExpect(jsonPath("$.data.totalElements").value(50))
                .andExpect(jsonPath("$.data.reviews.size()").value(5))
                .andExpect(jsonPath("$.data.reviews[*].id", Matchers.contains(1, 2, 3, 4, 5)));
    }

    @DisplayName("별점 높은 수강 후기들 조회 API")
    @Test
    void getScopeReviewsScore() throws Exception {
        // given

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/lectures/scope/reviews")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("강의력 좋은 강의 조회 API")
    @Test
    void getScopeLecturesBest() throws Exception {
        // given

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/lectures/scope/lectures")
                )
                .andDo(print())
                .andExpect(status().isOk());
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
