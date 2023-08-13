package dnd.project.domain.lecture.service;

import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.repository.LectureRepository;
import dnd.project.domain.lecture.response.LectureListReadResponse;
import dnd.project.domain.lecture.response.LectureScopeListReadResponse;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.review.repository.ReviewRepository;
import dnd.project.domain.user.entity.Authority;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import dnd.project.global.common.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dnd.project.global.common.Result.NOT_FOUND_MAIN_AND_SUB_CATEGORY;
import static dnd.project.global.common.Result.NOT_FOUND_MAIN_CATEGORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LectureServiceTest {

    @Autowired
    LectureService lectureService;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;

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

    @BeforeEach
    void beforeAll() {
        lectureRepository.saveAll(lectures);
    }

    @DisplayName("강의 검색 - 메인 카테고리, 서브 카테고리가 유효하지 않을 때")
    @Test
    void getLectures1() {
        Assertions.assertThatThrownBy(() ->
                        lectureService.getLectures(999,
                                1,
                                null,
                                0,
                                10,
                                null))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("result", NOT_FOUND_MAIN_AND_SUB_CATEGORY);
    }

    @DisplayName("강의 검색 - 메인 카테고리만 존재하고 유효하지 않을 때")
    @Test
    void getLectures2() {
        Assertions.assertThatThrownBy(() ->
                        lectureService.getLectures(999,
                                null,
                                null,
                                0,
                                10,
                                null))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("result", NOT_FOUND_MAIN_CATEGORY);
    }

    @DisplayName("강의 검색 - 서브 카테고리만 존재하고 유효하지 않을 때")
    @Test
    void getLectures3() {
        Assertions.assertThatThrownBy(() ->
                        lectureService.getLectures(null,
                                1,
                                null,
                                0,
                                10,
                                null))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("result", NOT_FOUND_MAIN_CATEGORY);
    }

    @DisplayName("강의 검색 - 카테고리가 없을 때 전체 검색")
    @Test
    void getLectures4() {
        LectureListReadResponse response = lectureService.getLectures(null,
                null,
                null,
                0,
                10,
                null);

        Assertions.assertThat(response.getLectures().size()).isEqualTo(lectures.size());
    }

    @DisplayName("scope 메인 정보 및 후기, 강의를 로그인 하지 않고 조회한다.")
    @Test
    void getScopeLectures() {
        // given
        // 사용자 생성
        Users user1 = saveUser("test1@test.com", "테스터1");
        Users user2 = saveUser("test2@test.com", "테스터2");
        Users user3 = saveUser("test3@test.com", "테스터3");
        userRepository.saveAll(List.of(user1, user2, user3));

        // 강의 생성
        Lecture lecture1 = getLecture("웹 디자인의 기초", "프로그래밍");
        Lecture lecture2 = getLecture("자바 프로그래밍 실전 입문", "프로그래밍");
        Lecture lecture3 = getLecture("맛있는 파스타 요리", "요리");
        Lecture lecture4 = getLecture("게임 개발의 첫걸음", "게임");
        Lecture lecture5 = getLecture("그림으로 배우는 일본어", "디자인");
        Lecture lecture6 = getLecture("프론트엔드 개발 마스터 클래스", "프로그래밍");
        Lecture lecture7 = getLecture("컴퓨터 비전 기초", "프로그래밍");
        Lecture lecture8 = getLecture("디지털 아트 워크샵", "디자인");
        Lecture lecture9 = getLecture("영화 속 레시피", "요리");
        Lecture lecture10 = getLecture("모바일 게임 디자인", "디자인");
        Lecture lecture11 = getLecture("너무 쉬운 스프링 개발서", "프로그래밍");
        Lecture lecture12 = getLecture("파이썬 프로그래밍 기초", "프로그래밍");

        lectureRepository.saveAll(List.of(
                lecture1, lecture2, lecture3,
                lecture4, lecture5, lecture6,
                lecture7, lecture8, lecture9,
                lecture10, lecture11, lecture12));


        // 리뷰 생성
        Review review1a = getReview(lecture1, user1, 5.0, "이해가 잘돼요, ", "내용이 알찹니다!");
        Review review1b = getReview(lecture1, user2, 5.0, "뛰어난 강의력, ", "아주 퍼펙트한 강의 이군요");
        Review review1c = getReview(lecture1, user3, 5.0, "뛰어난 강의력, ", "이 강의를 듣고 개념이 달라졌습니다.");

        Review review2 = getReview(lecture2, user1, 0.5, "도움이 안되었어요, ", "윽... 너무별로");
        Review review3 = getReview(lecture3, user2, 1.0, "도움이 안되었어요, ", "소리가 안들리는데요");

        Review review4a = getReview(lecture4, user1, 4.5, "뛰어난 강의력, 이해가 잘돼요", "내용이 알찹니다!");
        Review review4b = getReview(lecture4, user2, 5.0, "뛰어난 강의력, 매우 적극적", "내용이 알찹니다!");
        Review review4c = getReview(lecture4, user3, 5.0, "뛰어난 강의력, ", "내용이 알찹니다!");

        Review review5 = getReview(lecture5, user1, 1.5, "매우 적극적, ", "내용이 알찹니다!");
        Review review6 = getReview(lecture6, user1, 2.0, "매우 적극적, 도움이 많이 됐어요, ", "내용이 알찹니다!");

        Review review7a = getReview(lecture7, user1, 4.0, "내용이 자세해요", "내용이 알찹니다!");
        Review review7b = getReview(lecture7, user2, 3.5, "뛰어난 강의력, ", "내용이 알찹니다!");
        Review review7c = getReview(lecture7, user3, 4.0, "내용이 자세해요, ", "내용이 알찹니다!");

        Review review8 = getReview(lecture8, user1, 3.5, "뛰어난 강의력, 듣기 좋은 목소리", "내용이 알찹니다!");
        Review review9 = getReview(lecture9, user1, 4.0, "듣기 좋은 목소리", "내용이 알찹니다!");
        Review review10 = getReview(lecture10, user1, 3.0, "보통이에요, ", "내용이 알찹니다!");

        reviewRepository.saveAll(List.of(
                review1a, review1b, review1c, review2,
                review3, review4a, review4b, review4c,
                review5, review6, review7a, review7b,
                review7c, review8, review9, review10
        ));

        // when
        LectureScopeListReadResponse response = lectureService.getScopeLectures(null);

        // then
        assertThat(response)
                .extracting("isAnonymous", "userName", "interests")
                .contains(true, "anonymous", "anonymous");

        // 뛰어난 강의력 기준
        assertThat(response.getBestLectures())
                .extracting("id", "title")
                .containsExactly(
                        tuple(lecture4.getId(), lecture4.getTitle()),
                        tuple(lecture1.getId(), lecture1.getTitle()),
                        tuple(lecture7.getId(), lecture7.getTitle()),
                        tuple(lecture8.getId(), lecture8.getTitle())
                );

        // 4.0 이상의 후기 랜덤 순서
        assertThat(response.getHighScoreReviews())
                .hasSize(9)
                .extracting("id", "score")
                .contains(
                        tuple(review1a.getId(), review1a.getScore()),
                        tuple(review1b.getId(), review1b.getScore()),
                        tuple(review1c.getId(), review1c.getScore()),
                        tuple(review4b.getId(), review4b.getScore()),
                        tuple(review4c.getId(), review4c.getScore()),
                        tuple(review4a.getId(), review4a.getScore()),
                        tuple(review7a.getId(), review7a.getScore()),
                        tuple(review7c.getId(), review7c.getScore()),
                        tuple(review9.getId(), review9.getScore())
                );
    }

    @DisplayName("scope 메인 정보 및 후기, 강의를 로그인 하고 조회한다.")
    @Test
    void getScopeLecturesWithLogin() {
        // given
        // 사용자 생성
        Users user1 = saveUser("test1@test.com", "테스터1", "요리,프로그래밍");
        Users user2 = saveUser("test2@test.com", "테스터2");
        Users user3 = saveUser("test3@test.com", "테스터3");
        userRepository.saveAll(List.of(user1, user2, user3));

        // 강의 생성
        Lecture lecture1 = getLecture("웹 디자인의 기초", "프로그래밍");
        Lecture lecture2 = getLecture("자바 프로그래밍 실전 입문", "프로그래밍");
        Lecture lecture3 = getLecture("맛있는 파스타 요리", "요리");
        Lecture lecture4 = getLecture("게임 개발의 첫걸음", "게임");
        Lecture lecture5 = getLecture("그림으로 배우는 일본어", "디자인");
        Lecture lecture6 = getLecture("프론트엔드 개발 마스터 클래스", "프로그래밍");
        Lecture lecture7 = getLecture("컴퓨터 비전 기초", "프로그래밍");
        Lecture lecture8 = getLecture("디지털 아트 워크샵", "디자인");
        Lecture lecture9 = getLecture("영화 속 레시피", "요리");
        Lecture lecture10 = getLecture("모바일 게임 디자인", "디자인");
        Lecture lecture11 = getLecture("너무 쉬운 스프링 개발서", "프로그래밍");
        Lecture lecture12 = getLecture("파이썬 프로그래밍 기초", "프로그래밍");

        lectureRepository.saveAll(List.of(
                lecture1, lecture2, lecture3,
                lecture4, lecture5, lecture6,
                lecture7, lecture8, lecture9,
                lecture10, lecture11, lecture12));


        // 리뷰 생성
        Review review1a = getReview(lecture1, user1, 5.0, "이해가 잘돼요, ", "내용이 알찹니다!");
        Review review1b = getReview(lecture1, user2, 5.0, "뛰어난 강의력, ", "아주 퍼펙트한 강의 이군요");
        Review review1c = getReview(lecture1, user3, 5.0, "뛰어난 강의력, ", "이 강의를 듣고 개념이 달라졌습니다.");

        Review review2 = getReview(lecture2, user1, 0.5, "도움이 안되었어요, ", "윽... 너무별로");
        Review review3 = getReview(lecture3, user2, 1.0, "도움이 안되었어요, ", "소리가 안들리는데요");

        Review review4a = getReview(lecture4, user1, 4.5, "뛰어난 강의력, 이해가 잘돼요", "내용이 알찹니다!");
        Review review4b = getReview(lecture4, user2, 5.0, "뛰어난 강의력, 매우 적극적", "내용이 알찹니다!");
        Review review4c = getReview(lecture4, user3, 5.0, "뛰어난 강의력, ", "내용이 알찹니다!");

        Review review5 = getReview(lecture5, user1, 1.5, "매우 적극적, ", "내용이 알찹니다!");

        Review review6 = getReview(lecture6, user1, 2.0, "매우 적극적, 도움이 많이 됐어요, ", "내용이 알찹니다!");

        Review review7a = getReview(lecture7, user1, 4.0, "내용이 자세해요", "내용이 알찹니다!");
        Review review7b = getReview(lecture7, user2, 3.5, "뛰어난 강의력, ", "내용이 알찹니다!");
        Review review7c = getReview(lecture7, user3, 4.0, "내용이 자세해요, ", "내용이 알찹니다!");

        Review review8 = getReview(lecture8, user1, 3.5, "뛰어난 강의력, 듣기 좋은 목소리", "내용이 알찹니다!");
        Review review9 = getReview(lecture9, user1, 4.0, "듣기 좋은 목소리", "내용이 알찹니다!");
        Review review10 = getReview(lecture10, user1, 3.0, "보통이에요, ", "내용이 알찹니다!");

        reviewRepository.saveAll(List.of(
                review1a, review1b, review1c, review2,
                review3, review4a, review4b, review4c,
                review5, review6, review7a, review7b,
                review7c, review8, review9, review10
        ));

        // when
        LectureScopeListReadResponse response = lectureService.getScopeLectures(user1.getId());

        // then
        assertThat(response)
                .extracting("isAnonymous", "userName", "interests")
                .contains(false, "테스터1", "요리,프로그래밍");

        // 뛰어난 강의력 기준
        assertThat(response.getBestLectures())
                .extracting("id", "title")
                .hasSize(2)
                .containsExactly(
                        tuple(lecture1.getId(), lecture1.getTitle()),
                        tuple(lecture7.getId(), lecture7.getTitle())
                );

        // 4.0 이상의 후기 랜덤 순서
        assertThat(response.getHighScoreReviews())
                .hasSize(6)
                .extracting("id", "score")
                .contains(
                        tuple(review1a.getId(), review1a.getScore()),
                        tuple(review1b.getId(), review1b.getScore()),
                        tuple(review1c.getId(), review1c.getScore()),
                        tuple(review7a.getId(), review7a.getScore()),
                        tuple(review7c.getId(), review7c.getScore()),
                        tuple(review9.getId(), review9.getScore())
                );
    }

    @DisplayName("scope 메인 정보 및 후기, 강의를 조회할때 강의들이 리뷰가 없다.")
    @Test
    void getScopeLecturesWithEmptyReviews() {
        // given
        // 강의 생성
        Lecture lecture1 = getLecture("웹 디자인의 기초", "프로그래밍");
        Lecture lecture2 = getLecture("자바 프로그래밍 실전 입문", "프로그래밍");
        Lecture lecture3 = getLecture("맛있는 파스타 요리", "요리");
        Lecture lecture4 = getLecture("게임 개발의 첫걸음", "게임");
        Lecture lecture5 = getLecture("그림으로 배우는 일본어", "디자인");
        Lecture lecture6 = getLecture("프론트엔드 개발 마스터 클래스", "프로그래밍");
        Lecture lecture7 = getLecture("컴퓨터 비전 기초", "프로그래밍");
        Lecture lecture8 = getLecture("디지털 아트 워크샵", "디자인");
        Lecture lecture9 = getLecture("영화 속 레시피", "요리");
        Lecture lecture10 = getLecture("모바일 게임 디자인", "디자인");
        Lecture lecture11 = getLecture("너무 쉬운 스프링 개발서", "프로그래밍");
        Lecture lecture12 = getLecture("파이썬 프로그래밍 기초", "프로그래밍");

        lectureRepository.saveAll(List.of(
                lecture1, lecture2, lecture3,
                lecture4, lecture5, lecture6,
                lecture7, lecture8, lecture9,
                lecture10, lecture11, lecture12));

        // when
        LectureScopeListReadResponse response = lectureService.getScopeLectures(null);

        // then
        assertThat(response)
                .extracting("isAnonymous", "userName", "interests")
                .contains(true, "anonymous", "anonymous");

        // 뛰어난 강의력 기준
        assertThat(response.getBestLectures().isEmpty()).isTrue();

        // 4.0 이상의 후기 랜덤 순서
        assertThat(response.getHighScoreReviews().isEmpty()).isTrue();
    }

    // method

    private static Review getReview(
            Lecture lecture, Users randomUser, double score, String tags, String content
    ) {
        return Review.builder()
                .user(randomUser)
                .lecture(lecture)
                .score(score)
                .tags(tags)
                .content(content)
                .build();
    }

    private static Lecture getLecture(String randomLectureTitle, String randomMainCategory) {
        return Lecture.builder()
                .title(randomLectureTitle)
                .source("출처")
                .url("URL")
                .price("가격")
                .name("이름")
                .mainCategory(randomMainCategory)
                .subCategory("하위 카테고리")
                .keywords("키워드1, 키워드2, 키워드3")
                .content("강의 내용")
                .imageUrl("이미지 URL")
                .build();
    }

    private Users saveUser(String email, String nickname) {
        return Users.builder()
                .email(email)
                .password("password")
                .imageUrl("이미지 URL ")
                .nickName(nickname)
                .interests("관심사1, 관심사2")
                .authority(Authority.ROLE_USER)
                .build();
    }

    private Users saveUser(String email, String nickname, String interests) {
        return Users.builder()
                .email(email)
                .password("password")
                .imageUrl("이미지 URL ")
                .nickName(nickname)
                .interests(interests)
                .authority(Authority.ROLE_USER)
                .build();
    }
}