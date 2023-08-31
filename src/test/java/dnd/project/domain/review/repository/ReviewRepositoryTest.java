package dnd.project.domain.review.repository;

import dnd.project.domain.TestQuerydslConfig;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.repository.LectureRepository;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.user.entity.Authority;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dnd.project.domain.user.entity.Authority.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestQuerydslConfig.class)
@Transactional
class ReviewRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @DisplayName("가장 최근에 올라온 후기 10개를 가져온다.")
    @Test
    void findByRecentReview() {
        // given
        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);

        Review review1 = toEntityReview(lecture, user, 4.5, "아주 재밌습니다.");
        Review review2 = toEntityReview(lecture, user, 2.5, "그럭저럭이네요");
        Review review3 = toEntityReview(lecture, user, 3.5, "잘 들었습니다.");
        Review review4 = toEntityReview(lecture, user, 3.0, "들을만 했습니다.");
        Review review5 = toEntityReview(lecture, user, 4.0, "강의가 알차네요");
        Review review6 = toEntityReview(lecture, user, 5.0, "정말 퍼펙트한 강의입니다");
        Review review7 = toEntityReview(lecture, user, 2.5, "그냥..보통");
        Review review8 = toEntityReview(lecture, user, 1.5, "조금 더 준비를 해야할 것 같네요");
        Review review9 = toEntityReview(lecture, user, 1.0, "강의가 돈이 약간 아깝습니다.");
        Review review10 = toEntityReview(lecture, user, 0.5, "돈 버렸네요");
        Review review11 = toEntityReview(lecture, user, 2.0, "음...");
        Review review12 = toEntityReview(lecture, user, 2.0, "그냥...그래");

        reviewRepository.saveAll(
                List.of(
                        review1, review2, review3,
                        review4, review5, review6,
                        review7, review8, review9,
                        review10, review11, review12
                )
        );

        // when
        List<Review> reviews = reviewRepository.findByRecentReview();

        // then
        assertThat(reviews)
                .hasSize(10)
                .extracting("score")
                .contains(2.0, 2.0, 0.5, 1.0, 1.5, 2.5, 5.0, 4.0, 3.0, 3.5);
    }

//    @DisplayName("입력받은 키워드가 포함된 리뷰를 랜덤하게 가져온다.")
//    @Test
//    void findByKeyword() {
//        // given
//        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
//        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);
//
//        Review review1 = toEntityReview(lecture, user, 4.5, "아주 재밌습니다.", "빠른 답변,도움이 많이 됐어요");
//        Review review2 = toEntityReview(lecture, user, 2.5, "그럭저럭이네요", "빠른 답변,도움이 많이 됐어요");
//        Review review3 = toEntityReview(lecture, user, 3.5, "잘 들었습니다.", "빠른 답변,커리큘럼과 똑같아요");
//        Review review4 = toEntityReview(lecture, user, 3.0, "들을만 했습니다.", "뛰어난 강의력");
//        Review review5 = toEntityReview(lecture, user, 4.0, "강의가 알차네요", "구성이 알차요");
//        Review review6 = toEntityReview(lecture, user, 5.0, "정말 퍼펙트한 강의입니다", "커리큘럼과 똑같아요,구성이 알차요");
//        Review review7 = toEntityReview(lecture, user, 2.5, "그냥..보통", "커리큘럼과 똑같아요, 듣기 좋은 목소리");
//        Review review8 = toEntityReview(lecture, user, 1.5, "조금 더 준비를 해야할 것 같네요", "도움이 많이 됐어요,구성이 알차요");
//        Review review9 = toEntityReview(lecture, user, 1.0, "강의가 돈이 약간 아깝습니다.", "커리큘럼과 똑같아요");
//        Review review10 = toEntityReview(lecture, user, 0.5, "돈 버렸네요", "빠른 답변");
//        Review review11 = toEntityReview(lecture, user, 2.0, "음...", "도움이 많이 됐어요");
//        Review review12 = toEntityReview(lecture, user, 2.0, "그냥...그래", "빠른 답변,도움이 많이 됐어요,커리큘럼과 똑같아요");
//
//        reviewRepository.saveAll(
//                List.of(
//                        review1, review2, review3, review4,
//                        review5, review6, review7, review8,
//                        review9, review10, review11, review12)
//        );
//
//        // when
//        List<LectureScopeListReadResponse.DetailReview> detailReviews =
//                reviewRepository.findByKeyword("커리큘럼과 똑같아요");
//
//        // then
//        assertThat(detailReviews)
//                .extracting("id", "lectureTitle", "userName", "score")
//                .contains(
//                        tuple(review3.getId(), review3.getLecture().getTitle(), "test", review3.getScore()),
//                        tuple(review6.getId(), review6.getLecture().getTitle(), "test", review6.getScore()),
//                        tuple(review7.getId(), review7.getLecture().getTitle(), "test", review7.getScore()),
//                        tuple(review9.getId(), review9.getLecture().getTitle(), "test", review9.getScore()),
//                        tuple(review12.getId(), review12.getLecture().getTitle(), "test", review12.getScore())
//                );
//    }

    private static Review toEntityReview(Lecture lecture, Users user, Double score, String content) {
        return Review.builder()
                .user(user)
                .lecture(lecture)
                .tags("빠른 답변,이해가 잘돼요,보통이에요")
                .score(score)
                .content(content)
                .build();
    }

    private static Review toEntityReview(Lecture lecture, Users user, Double score, String content, String tags) {
        return Review.builder()
                .user(user)
                .lecture(lecture)
                .tags(tags)
                .score(score)
                .content(content)
                .build();
    }

    private Lecture saveLecture(String title, String mainCategory, String subCategory, String keywords) {
        return lectureRepository.save(
                Lecture.builder()
                        .title(title)
                        .source("인프런")
                        .url("https://www.inflearn.com/course/practical-testing-실용적인-테스트-가이드/dashboard")
                        .price("53900")
                        .name("박우빈")
                        .mainCategory(mainCategory)
                        .subCategory(subCategory)
                        .keywords(keywords)
                        .imageUrl("test")
                        .build()
        );
    }

    private Users saveUser(String email, String password, String nickName, Authority authority) {
        return userRepository.save(
                Users.builder()
                        .email(email)
                        .password(password)
                        .nickName(nickName)
                        .authority(authority)
                        .build()
        );
    }
}

