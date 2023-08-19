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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static dnd.project.domain.user.entity.Authority.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
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

//    @DisplayName("유저가 강의에 하나의 리뷰를 남긴다.")
//    @Test
//    void createReview() {
//        // given
//        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
//        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);
//
//        ReviewRequest.Create request =
//                new ReviewRequest.Create(lecture.getId(), 4.0, List.of("빠른 답변", "이해가 잘돼요", "보통이에요"), null);
//
//        // when
//        ReviewResponse.Create response =
//                reviewService.createReview(request.toServiceRequest(), user.getId());
//
//        // then
//        assertThat(response)
//                .extracting("lectureId", "userId", "nickName", "score", "tags", "content", "createdDate")
//                .contains(
//                        lecture.getId(), user.getId(), user.getNickName(),
//                        4.0, "빠른 답변,이해가 잘돼요,보통이에요", "",
//                        // TODO : now 수정해야됨
//                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
//                );
//    }

    @DisplayName("유저가 강의에 두개 이상의 리뷰를 남기려 할때 Exception 발생한다.")
    @Test
    void CreateMultipleReviewsException() {
        // given
        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);
        Review review = saveReview(lecture, user, 4.0, "");

        ReviewRequest.Create request =
                new ReviewRequest.Create(lecture.getId(), 4.0, List.of("빠른 답변", "이해가 잘돼요", "보통이에요"), null);

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

        String isAddLikeKey = String.format("%s : %s", review.getId(), user2.getId());
        redisDao.deleteValues(isAddLikeKey);

        // when
        ReviewResponse.ToggleLike response =
                reviewService.toggleLikeReview(review.getId(), user2.getId());

        // then
        assertThat(response)
                .extracting("reviewId", "userId", "isCancelled")
                .contains(review.getId(), user2.getId(), false);

        redisDao.deleteValues(isAddLikeKey);
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
        redisDao.setValues(String.format("%s : %s", review.getId(), user2.getId()), "Y");

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

        entityManager.flush();
        entityManager.clear();

        // when
        assertThatThrownBy(() -> reviewService.toggleLikeReview(review.getId(), user.getId()))
                .extracting("result.code", "result.message")
                .contains(-3003, "내가 작성한 후기에는 좋아요를 남길 수 없습니다.");

    }

    @DisplayName("사용자가 로그인 후 가장 최근 후기 10개를 불러온다.")
    @Test
    void readRecentReview() {
        // given
        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);
        Users user2 = saveUser("test2@test.com", "test", "test", ROLE_USER);

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

        likeReviewRepository.save(
                LikeReview.builder()
                        .users(user2)
                        .review(review11)
                        .build()
        );

        entityManager.flush();
        entityManager.clear();

        // when
        List<ReviewResponse.ReadDetails> response = reviewService.readRecentReview(user2.getId());

        // then
        assertThat(response)
                .extracting("review.reviewId", "lecture.mainCategory", "review.score", "review.likes", "isAddLike")
                .contains(
                        tuple(review12.getId(), "프로그래밍", 2.0, 0, false),
                        tuple(review11.getId(), "프로그래밍", 2.0, 1, true),
                        tuple(review10.getId(), "프로그래밍", 0.5, 0, false),
                        tuple(review9.getId(), "프로그래밍", 1.0, 0, false),
                        tuple(review8.getId(), "프로그래밍", 1.5, 0, false),
                        tuple(review7.getId(), "프로그래밍", 2.5, 0, false),
                        tuple(review6.getId(), "프로그래밍", 5.0, 0, false),
                        tuple(review5.getId(), "프로그래밍", 4.0, 0, false),
                        tuple(review4.getId(), "프로그래밍", 3.0, 0, false),
                        tuple(review3.getId(), "프로그래밍", 3.5, 0, false)
                );
    }

    @DisplayName("사용자가 로그인 하지 않고 가장 최근 후기 10개를 불러온다.")
    @Test
    void readRecentReviewNotLogin() {
        // given
        Lecture lecture = saveLecture("실용적인 테스트 가이드", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);
        Users user2 = saveUser("test2@test.com", "test", "test", ROLE_USER);

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

        likeReviewRepository.save(
                LikeReview.builder()
                        .users(user2)
                        .review(review11)
                        .build()
        );

        entityManager.flush();
        entityManager.clear();

        // when
        List<ReviewResponse.ReadDetails> response = reviewService.readRecentReview(null);

        // then
        assertThat(response)
                .extracting("review.reviewId", "review.score", "review.likes", "isAddLike")
                .contains(
                        tuple(review12.getId(), 2.0, 0, false),
                        tuple(review11.getId(), 2.0, 1, false),
                        tuple(review10.getId(), 0.5, 0, false),
                        tuple(review9.getId(), 1.0, 0, false),
                        tuple(review8.getId(), 1.5, 0, false),
                        tuple(review7.getId(), 2.5, 0, false),
                        tuple(review6.getId(), 5.0, 0, false),
                        tuple(review5.getId(), 4.0, 0, false),
                        tuple(review4.getId(), 3.0, 0, false),
                        tuple(review3.getId(), 3.5, 0, false)
                );
    }

    @DisplayName("유저가 자신이 작성한 후기를 본다.")
    @Test
    void readMyReviews() {
        // given
        Lecture lecture1 = toEntityLecture("실용적인 테스트 가이드1", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Lecture lecture2 = toEntityLecture("실용적인 테스트 가이드2", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Lecture lecture3 = toEntityLecture("실용적인 테스트 가이드3", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        lectureRepository.saveAll(List.of(lecture1, lecture2, lecture3));

        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);
        Users user2 = saveUser("test2@test.com", "test", "test", ROLE_USER);

        Review review1 = toEntityReview(lecture1, user, 4.5, "아주 재밌습니다.");
        Review review2 = toEntityReview(lecture2, user, 4.0, "아주 재밌습니다.");
        Review review3 = toEntityReview(lecture3, user, 5.0, "아주 재밌습니다.");

        reviewRepository.saveAll(List.of(review1, review2, review3));

        entityManager.flush();
        entityManager.clear();

        likeReviewRepository.save(
                LikeReview.builder()
                        .users(user2)
                        .review(review3)
                        .build()
        );

        // when
        List<ReviewResponse.ReadMyDetails> response = reviewService.readMyReviews(user.getId());

        // then
        assertThat(response)
                .extracting("review.reviewId", "review.likes", "review.score", "lecture.title")
                .contains(
                        tuple(review1.getId(), 0, 4.5, "실용적인 테스트 가이드1"),
                        tuple(review2.getId(), 0, 4.0, "실용적인 테스트 가이드2"),
                        tuple(review3.getId(), 1, 5.0, "실용적인 테스트 가이드3")
                );
    }

    @DisplayName("유저가 자신이 남긴 후기를 볼때 작성한 후기가 없다면 Exception 이 발생한다.")
    @Test
    void readMyReviewsWithNotCreateReview() {
        // given
        Lecture lecture1 = toEntityLecture("실용적인 테스트 가이드1", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Lecture lecture2 = toEntityLecture("실용적인 테스트 가이드2", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Lecture lecture3 = toEntityLecture("실용적인 테스트 가이드3", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        lectureRepository.saveAll(List.of(lecture1, lecture2, lecture3));

        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);

        entityManager.flush();
        entityManager.clear();

        // when
        assertThatThrownBy(() -> reviewService.readMyReviews(user.getId()))
                .extracting("result.code", "result.message")
                .contains(-3004, "아직 후기를 한번도 작성하지 않았습니다.");
    }

    // method

    private Review saveReview(Lecture lecture, Users user, Double score, String content) {
        return reviewRepository.save(
                toEntityReview(lecture, user, score, content)
        );
    }

    private static Review toEntityReview(Lecture lecture, Users user, Double score, String content) {
        return Review.builder()
                .user(user)
                .lecture(lecture)
                .tags("빠른 답변,이해가 잘돼요,보통이에요")
                .score(score)
                .content(content)
                .build();
    }

    private Lecture saveLecture(String title, String mainCategory, String subCategory, String keywords) {
        return lectureRepository.save(
                toEntityLecture(title, mainCategory, subCategory, keywords)
        );
    }

    private static Lecture toEntityLecture(String title, String mainCategory, String subCategory, String keywords) {
        return Lecture.builder()
                .title(title)
                .source("인프런")
                .url("https://www.inflearn.com/course/practical-testing-실용적인-테스트-가이드/dashboard")
                .price("53900")
                .name("박우빈")
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .keywords(keywords)
                .imageUrl("test")
                .build();
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