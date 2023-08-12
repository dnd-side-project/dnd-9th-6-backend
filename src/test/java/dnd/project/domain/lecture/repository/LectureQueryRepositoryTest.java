package dnd.project.domain.lecture.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dnd.project.domain.bookmark.entity.Bookmark;
import dnd.project.domain.bookmark.repository.BookmarkRepository;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.review.repository.ReviewRepository;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
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
import org.springframework.test.annotation.Commit;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(LectureQueryRepositoryTest.TestQuerydslConfig.class)
@DataJpaTest
class LectureQueryRepositoryTest {

    @Autowired
    LectureQueryRepository lectureQueryRepository;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    ReviewRepository reviewRepository;
    List<Lecture> lectures = List.of(
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

    List<Users> users = List.of(
            Users.builder().email("user1@gmail.com").password("").nickName("user1").build(),
            Users.builder().email("user2@gmail.com").password("").nickName("user2").build(),
            Users.builder().email("user3@gmail.com").password("").nickName("user3").build());

    // Lecture 0(3개), Lecture 2(1개), Lecture 1(1개), Lecture 3(0개), Lecture 4(0개)
    List<Bookmark> bookmarks = List.of(
            Bookmark.builder()
                    .lecture(lectures.get(0))
                    .user(users.get(0))
                    .build(),
            Bookmark.builder()
                    .lecture(lectures.get(0))
                    .user(users.get(1))
                    .build(),
            Bookmark.builder()
                    .lecture(lectures.get(0))
                    .user(users.get(2))
                    .build(),
            Bookmark.builder()
                    .lecture(lectures.get(2))
                    .user(users.get(1))
                    .build(),
            Bookmark.builder()
                    .lecture(lectures.get(1))
                    .user(users.get(2))
                    .build());

    // Lecture 4(3개), Lecture 2(2개), Lecture 3(1개), Lecture 1(1개), Lecture 0(0개)
    List<Review> reviews = List.of(
            Review.builder()
                    .lecture(lectures.get(4))
                    .user(users.get(0))
                    .score(5.0)
                    .tags("좋아요")
                    .content("iOS 개발 추천 강의")
                    .build(),
            Review.builder()
                    .lecture(lectures.get(4))
                    .user(users.get(1))
                    .score(5.0)
                    .tags("좋아요2")
                    .content("iOS 개발 추천 강의2")
                    .build(),
            Review.builder()
                    .lecture(lectures.get(4))
                    .user(users.get(2))
                    .score(5.0)
                    .tags("좋아요3")
                    .content("iOS 개발 추천 강의3")
                    .build(),
            Review.builder()
                    .lecture(lectures.get(2))
                    .user(users.get(2))
                    .score(5.0)
                    .tags("좋아요4")
                    .content("스프링 개발 추천 강의")
                    .build(),
            Review.builder()
                    .lecture(lectures.get(2))
                    .user(users.get(1))
                    .score(5.0)
                    .tags("좋아요5")
                    .content("스프링 개발 추천 강의")
                    .build(),
            Review.builder()
                    .lecture(lectures.get(3))
                    .user(users.get(0))
                    .score(5.0)
                    .tags("좋아요")
                    .content("리액트 네이티브 개발 추천 강의")
                    .build(),
            Review.builder()
                    .lecture(lectures.get(1))
                    .user(users.get(0))
                    .score(5.0)
                    .tags("좋아요")
                    .content("스프링 개발 추천 강의")
                    .build());

    @BeforeEach
    void beforeAll() {
        lectureRepository.saveAll(lectures);
        userRepository.saveAll(users);
        bookmarkRepository.saveAll(bookmarks);
        reviewRepository.saveAll(reviews);
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

    @DisplayName("강의 검색 - 북마크수 오름 차순")
    @Test
    void findAll11() {
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍",
                null,
                null,
                0,
                2,
                "bookmark,asc");
        List<Lecture> content = lectures.getContent();
        Assertions.assertThat(content.get(0).getTitle()).isEqualTo(this.lectures.get(3).getTitle());
        Assertions.assertThat(content.get(1).getTitle()).isEqualTo(this.lectures.get(4).getTitle());
    }

    @DisplayName("강의 검색 - 북마크수 내림 차순")
    @Test
    void findAll12() {
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍",
                null,
                null,
                0,
                2,
                "bookmark,desc");
        List<Lecture> content = lectures.getContent();
        Assertions.assertThat(content.get(0).getTitle()).isEqualTo(this.lectures.get(0).getTitle());
        Assertions.assertThat(content.get(1).getTitle()).isEqualTo(this.lectures.get(1).getTitle());
    }

    @DisplayName("강의 검색 - 리뷰수 오름 차순")
    @Test
    void findAll13() {
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍",
                null,
                null,
                0,
                2,
                "review,asc");
        List<Lecture> content = lectures.getContent();
        Assertions.assertThat(content.get(0).getTitle()).isEqualTo(this.lectures.get(0).getTitle());
        Assertions.assertThat(content.get(1).getTitle()).isEqualTo(this.lectures.get(1).getTitle());
    }

    @DisplayName("강의 검색 - 리뷰수 내림 차순")
    @Test
    @Commit
    void findAll14() {
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍",
                null,
                null,
                0,
                5,
                "review,desc");
        List<Lecture> content = lectures.getContent();
        Assertions.assertThat(content.get(0).getTitle()).isEqualTo(this.lectures.get(4).getTitle());
        Assertions.assertThat(content.get(1).getTitle()).isEqualTo(this.lectures.get(2).getTitle());
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