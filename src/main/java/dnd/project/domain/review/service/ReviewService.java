package dnd.project.domain.review.service;

import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.repository.LectureRepository;
import dnd.project.domain.review.entity.LikeReview;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.review.repository.LikeReviewRepository;
import dnd.project.domain.review.repository.ReviewRepository;
import dnd.project.domain.review.request.ReviewServiceRequest;
import dnd.project.domain.review.response.ReviewResponse;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import dnd.project.global.common.exception.CustomException;
import dnd.project.global.config.redis.RedisDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static dnd.project.global.common.Result.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final LikeReviewRepository likeReviewRepository;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;
    private final ReviewRepository reviewRepository;

    private final RedisDao redisDao;

    // 후기 작성 API
    @Transactional
    public ReviewResponse.Create createReview(ReviewServiceRequest.Create request, Long userId) {
        Users user = getUser(userId);
        Lecture lecture = getLecture(request.getLectureId());
        Boolean isCreated = reviewRepository.existsByLectureAndUser(lecture, user);

        if (isCreated.equals(TRUE)) {
            throw new CustomException(ALREADY_CREATED_REVIEW);
        }

        List<String> tags = request.getTags();

        Review review = reviewRepository.save(
                toEntityReview(request.getScore(), String.join(",", tags), request.getContent(), user, lecture)
        );

        return ReviewResponse.Create.response(review, lecture, user);
    }

    // 후기 삭제 API
    @Transactional
    public Void deleteReview(Long reviewId, Long userId) {
        Review review = getReview(reviewId);

        notMatchedUserValidate(userId, review);

        reviewRepository.deleteById(review.getId());
        return null;
    }

    // 후기 수정 API
    @Transactional
    public ReviewResponse.Create updateReview(ReviewServiceRequest.Update request, Long userId) {
        Review review = getReview(request.getReviewId());

        notMatchedUserValidate(userId, review);

        review.toUpdateReview(request.getScore(), request.getTags(), request.getContent().orElse(""));
        return ReviewResponse.Create.response(review, review.getLecture(), review.getUser());
    }

    // 후기 좋아요 및 취소 API
    @Transactional
    public ReviewResponse.ToggleLike toggleLikeReview(Long reviewId, Long userId) {
        Review review = getReview(reviewId);
        Users user = getUser(userId);

        if (review.getUser().getId().equals(user.getId())) {
            throw new CustomException(NOT_MY_REVIEW_LIKE);
        }
        // Redis 를 통한 좋아요 등록 여부 확인
        String isAddLikeKey = userId + " like";
        Optional<String> isAddLike = Optional.ofNullable(redisDao.getValues(isAddLikeKey));

        Boolean isCancelled;
        // 이미 좋아요를 남긴 경우 취소
        if (isAddLike.isPresent()) {
            redisDao.deleteValues(isAddLikeKey);
            likeReviewRepository.deleteByReviewAndUsers(review, user);
            isCancelled = TRUE;
        }
        // 좋아요를 처음 남기는 경우 등록
        else {
            redisDao.setValues(isAddLikeKey, "Y");
            likeReviewRepository.save(toEntityLikeReview(review, user));
            isCancelled = FALSE;
        }

        return ReviewResponse.ToggleLike.response(review, user, isCancelled);
    }

    // 최근 올라온 후기 조회 API
    public List<ReviewResponse.ReadDetails> readRecentReview(Long userId) {
        List<Review> reviews =
                reviewRepository.findByRecentReview();

        return reviews.stream().map(review -> ReviewResponse.ReadDetails.response(
                review,
                review.getLecture(),
                review.getUser(),
                checkLiked(userId, review))
        ).toList();
    }

    // 내 후기 조회 API
    public List<ReviewResponse.ReadMyDetails> readMyReviews(Long userId) {
        List<Review> reviews = reviewRepository.findByMyReview(userId);

        if (reviews.isEmpty()) {
            throw new CustomException(NOT_CREATED_REVIEW);
        }

        return reviews.stream().map(review -> ReviewResponse.ReadMyDetails.response(
                review, review.getLecture()
        )).toList();
    }

    // method

    private static boolean checkLiked(Long userId, Review review) {
        boolean isLiked;
        if (userId == null) {
            isLiked = false;
        } else {
            isLiked = review.getLikeReviews().stream()
                    .anyMatch(likeReview -> likeReview.getUsers()
                            .getId()
                            .equals(userId)
                    );
        }
        return isLiked;
    }

    private static LikeReview toEntityLikeReview(Review review, Users user) {
        return LikeReview.builder()
                .users(user)
                .review(review)
                .build();
    }

    private static Review toEntityReview(
            Double score, String tags, Optional<String> optionalContent,
            Users user, Lecture lecture
    ) {
        return Review.builder()
                .user(user)
                .lecture(lecture)
                .score(score)
                .tags(tags)
                .content(optionalContent.orElse(""))
                .build();
    }

    private static void notMatchedUserValidate(Long userId, Review review) {
        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(NOT_MATCHED_USER);
        }
    }

    private Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new CustomException(NOT_FOUND_REVIEW)
        );
    }

    private Lecture getLecture(Long lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow(
                () -> new CustomException(NOT_FOUND_LECTURE)
        );
    }

    private Users getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
    }
}
