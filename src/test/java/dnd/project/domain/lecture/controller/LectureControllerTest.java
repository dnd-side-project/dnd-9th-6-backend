package dnd.project.domain.lecture.controller;

import dnd.project.domain.ControllerTestSupport;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.response.LectureListReadResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LectureControllerTest extends ControllerTestSupport {

    @DisplayName("강의 검색 API")
    @Test
    void getLectures() throws Exception {
        // Given
        List<Lecture> totalLectures = List.of(
                getLecture(1L, "스프링 부트 - 핵심 원리와 활용", "99000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture(2L, "스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture(3L, "스프링 DB 2편 - 데이터 접근 활용 기술", "99000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture(4L, "배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture(5L, "앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));


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
                        "앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지")));
    }

    @DisplayName("scope 페이지 추천 강의 조회 API")
    @Test
    void getScopeLectures() throws Exception {
        // given

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/lectures/scope")
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