package dnd.project.domain.review.service;

import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.repository.LectureRepository;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.review.repository.ReviewRepository;
import dnd.project.domain.review.request.ReviewRequest;
import dnd.project.domain.review.response.ReviewResponse;
import dnd.project.domain.user.entity.Authority;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static dnd.project.domain.user.entity.Authority.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReviewServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    @DisplayName("유저가 강의에 하나의 리뷰를 남긴다.")
    @Test
    void createReview() {
        // given
        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);

        ReviewRequest.Create request =
                new ReviewRequest.Create(lecture.getId(), 4, "빠른 답변,이해가 잘돼요,보통이에요", null);

        // when
        ReviewResponse.Create response =
                reviewService.createReview(request.toServiceRequest(), user.getId());

        // then
        assertThat(response)
                .extracting("lectureId", "userId", "nickName", "score", "tags", "content", "createdDate")
                .contains(
                        lecture.getId(), user.getId(), user.getNickName(),
                        4, "빠른 답변,이해가 잘돼요,보통이에요", "", "2023-08-09"
                );
    }

    @DisplayName("유저가 강의에 두개 이상의 리뷰를 남기려 할때 Exception 발생한다.")
    @Test
    void CreateMultipleReviewsException() {
        // given
        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);
        Review review = saveReview(lecture, user, 4, "");

        ReviewRequest.Create request =
                new ReviewRequest.Create(lecture.getId(), 4, "빠른 답변,이해가 잘돼요,보통이에요", null);

        // when // then
        assertThatThrownBy(() -> reviewService.createReview(request.toServiceRequest(), user.getId()))
                .extracting("result.code", "result.message")
                .contains(-3000, "이미 해당 강의의 후기를 작성하였습니다.");
    }

    // method

    private Review saveReview(Lecture lecture, Users user, Integer score, String content) {
        return reviewRepository.save(
                Review.builder()
                        .user(user)
                        .lecture(lecture)
                        .tags("빠른 답변,이해가 잘돼요,보통이에요")
                        .score(score)
                        .content(content)
                        .build()
        );
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