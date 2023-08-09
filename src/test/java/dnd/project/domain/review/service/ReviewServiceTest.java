package dnd.project.domain.review.service;

import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.repository.LectureRepository;
import dnd.project.domain.review.entity.LikeReview;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.review.repository.LikeReviewRepository;
import dnd.project.domain.review.repository.ReviewRepository;
import dnd.project.domain.review.request.ReviewRequest;
import dnd.project.domain.review.response.ReviewResponse;
import dnd.project.domain.user.entity.Authority;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import dnd.project.global.config.redis.RedisDao;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static dnd.project.domain.user.entity.Authority.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReviewServiceTest {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private LikeReviewRepository likeReviewRepository;

    @Autowired
    private EntityManager entityManager;

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
                new ReviewRequest.Create(lecture.getId(), 4.0, "빠른 답변,이해가 잘돼요,보통이에요", null);

        // when
        ReviewResponse.Create response =
                reviewService.createReview(request.toServiceRequest(), user.getId());

        // then
        assertThat(response)
                .extracting("lectureId", "userId", "nickName", "score", "tags", "content", "createdDate")
                .contains(
                        lecture.getId(), user.getId(), user.getNickName(),
                        4.0, "빠른 답변,이해가 잘돼요,보통이에요", "", "2023-08-09"
                );
    }

    @DisplayName("유저가 강의에 두개 이상의 리뷰를 남기려 할때 Exception 발생한다.")
    @Test
    void CreateMultipleReviewsException() {
        // given
        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);
        Review review = saveReview(lecture, user, 4.0, "");

        ReviewRequest.Create request =
                new ReviewRequest.Create(lecture.getId(), 4.0, "빠른 답변,이해가 잘돼요,보통이에요", null);

        // when // then
        assertThatThrownBy(() -> reviewService.createReview(request.toServiceRequest(), user.getId()))
                .extracting("result.code", "result.message")
                .contains(-3000, "이미 해당 강의의 후기를 작성하였습니다.");
    }

    @DisplayName("유저가 남겼던 후기를 삭제한다.")
    @Test
    void deleteReview() {
        // given
        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);
        Review review = saveReview(lecture, user, 4.0, "");

        entityManager.flush();
        entityManager.clear();

        // when
        reviewService.deleteReview(review.getId(), user.getId());

        // then
        Optional<Review> optionalReview = reviewRepository.findById(review.getId());
        assertThat(optionalReview.isEmpty()).isTrue();
    }

    @DisplayName("유저가 자신이 작성한 후기를 수정한다.")
    @Test
    void updateReview() {
        // given
        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);
        Review review = saveReview(lecture, user, 4.0, "");

        ReviewRequest.Update request =
                new ReviewRequest.Update(review.getId(), 3.5, "빠른 답변,이해가 잘돼요,도움이 안되었어요", "강의가 별로네요..");

        // when
        ReviewResponse.Create response =
                reviewService.updateReview(request.toServiceRequest(), user.getId());

        // then
        assertThat(response)
                .extracting("reviewId", "lectureId", "userId", "nickName", "score", "tags", "content")
                .contains(
                        review.getId(), lecture.getId(), user.getId(),
                        user.getNickName(), 3.5, "빠른 답변,이해가 잘돼요,도움이 안되었어요",
                        "강의가 별로네요.."
                );
    }

    @DisplayName("유저가 하나의 후기에 좋아요를 남긴다.")
    @Test
    void toggleLikeReviewIsAdd() {
        // given
        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Users user1 = saveUser("test1@test.com", "test", "test", ROLE_USER);
        Users user2 = saveUser("test2@test.com", "test", "test", ROLE_USER);
        Review review = saveReview(lecture, user1, 4.0, "");

        // when
        ReviewResponse.ToggleLike response =
                reviewService.toggleLikeReview(review.getId(), user2.getId());

        // then
        assertThat(response)
                .extracting("reviewId", "userId", "isCancelled")
                .contains(review.getId(), user2.getId(), false);

        redisDao.deleteValues(user2.getId() + " like");
    }

    @DisplayName("유저가 후기에 남긴 좋아요를 취소한다.")
    @Test
    void toggleLikeReviewIsCancel() {
        // given
        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Users user1 = saveUser("test1@test.com", "test", "test", ROLE_USER);
        Users user2 = saveUser("test2@test.com", "test", "test", ROLE_USER);
        Review review = saveReview(lecture, user1, 4.0, "");
        likeReviewRepository.save(
                LikeReview.builder()
                        .users(user2)
                        .review(review)
                        .build()
        );
        redisDao.setValues(user2.getId() + " like", "Y");

        entityManager.flush();
        entityManager.clear();

        // when
        ReviewResponse.ToggleLike response =
                reviewService.toggleLikeReview(review.getId(), user2.getId());

        // then
        assertThat(response)
                .extracting("reviewId", "userId", "isCancelled")
                .contains(review.getId(), user2.getId(), true);

    }

    @DisplayName("유저가 내가 남긴 후기에 좋아요를 남기는 경우 Exception 이 발생한다.")
    @Test
    void myReviewAddLikeThrowByException() {
        // given
        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);
        Review review = saveReview(lecture, user, 4.0, "");
        likeReviewRepository.save(
                LikeReview.builder()
                        .users(user)
                        .review(review)
                        .build()
        );
        redisDao.setValues(user.getId() + " like", "Y");

        entityManager.flush();
        entityManager.clear();

        // when
        assertThatThrownBy(() -> reviewService.toggleLikeReview(review.getId(), user.getId()))
                .extracting("result.code", "result.message")
                .contains(-3003, "내가 작성한 후기에는 좋아요를 남길 수 없습니다.");

    }

    // method

    private Review saveReview(Lecture lecture, Users user, Double score, String content) {
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