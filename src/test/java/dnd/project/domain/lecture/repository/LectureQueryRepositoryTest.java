package dnd.project.domain.lecture.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dnd.project.domain.lecture.entity.Lecture;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(LectureQueryRepositoryTest.TestQuerydslConfig.class)
@DataJpaTest
class LectureQueryRepositoryTest {

    @Autowired
    LectureQueryRepository lectureQueryRepository;
    @Autowired
    LectureRepository lectureRepository;

    static final List<Lecture> LECTURES = List.of(
            Lecture.builder()
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

    @BeforeEach
    void beforeAll() {
        lectureRepository.saveAll(LECTURES);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 없음")
    @Test
    void findAll1() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, null, 0, 2, null);

        // then
        Assertions.assertThat(lectures.getTotalPages()).isEqualTo(3);
        Assertions.assertThat(lectures.getTotalElements()).isEqualTo(5);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 스프링")
    @Test
    void findAll2() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "스프링", 0, 2, null);

        // then
        Assertions.assertThat(lectures.getTotalPages()).isEqualTo(2);
        Assertions.assertThat(lectures.getTotalElements()).isEqualTo(3);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 김영한")
    @Test
    void findAll3() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "김영한", 0, 2, null);

        // then
        Assertions.assertThat(lectures.getTotalPages()).isEqualTo(2);
        Assertions.assertThat(lectures.getTotalElements()).isEqualTo(3);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 iOS")
    @Test
    void findAll4() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "iOS", 0, 2, null);

        // then
        Assertions.assertThat(lectures.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(lectures.getTotalElements()).isEqualTo(1);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 백엔드")
    @Test
    void findAll5() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "백엔드", 0, 2, null);

        // then
        Assertions.assertThat(lectures.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(lectures.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 앱")
    @Test
    void findAll6() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "앱", 0, 2, null);

        // then
        Assertions.assertThat(lectures.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(lectures.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 네이티브")
    @Test
    void findAll7() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "네이티브", 0, 2, null);

        // then
        Assertions.assertThat(lectures.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(lectures.getTotalElements()).isEqualTo(1);
    }

    @DisplayName("강의 검색 - 메인 카테고리 프로그래밍, 키워드 없음")
    @Test
    void findAll8() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍", null, null, 0, 2, null);

        // then
        Assertions.assertThat(lectures.getTotalPages()).isEqualTo(3);
        Assertions.assertThat(lectures.getTotalElements()).isEqualTo(5);
    }

    @DisplayName("강의 검색 - 메인 카테고리 프로그래밍, 서브 카테고리 앱, 키워드 없음")
    @Test
    void findAll9() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍", "앱", null, 0, 2, null);

        // then
        Assertions.assertThat(lectures.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(lectures.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("강의 검색 - 메인 카테고리 프로그래밍, 서브 카테고리 웹, 키워드 없음")
    @Test
    void findAll10() {
        // given
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍", "웹", null, 0, 2, null);

        // then
        Assertions.assertThat(lectures.getTotalPages()).isEqualTo(2);
        Assertions.assertThat(lectures.getTotalElements()).isEqualTo(3);
    }

    @RequiredArgsConstructor
    @TestConfiguration
    public static class TestQuerydslConfig {
        private final EntityManager entityManager;

        @Bean
        public JPAQueryFactory queryFactory() {
            return new JPAQueryFactory(entityManager);
        }

        @Bean
        public LectureQueryRepository lectureQueryRepository() {
            return new LectureQueryRepository(queryFactory());
        }
    }
}