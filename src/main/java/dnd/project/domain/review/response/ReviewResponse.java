package dnd.project.domain.review.response;


import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

public class ReviewResponse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Create {
        private Long reviewId;
        private Long lectureId;
        private Long userId;
        private String nickName;
        private Double score;
        private String tags;
        private String content;
        private String createdDate;

        public static ReviewResponse.Create response(Review review, Lecture lecture, Users user) {
            return ReviewResponse.Create.builder()
                    .reviewId(review.getId())
                    .lectureId(lecture.getId())
                    .userId(user.getId())
                    .nickName(user.getNickName())
                    .score(review.getScore())
                    .tags(review.getTags())
                    .content(review.getContent())
                    .createdDate(review.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class ToggleLike {
        private Long reviewId;
        private Long userId;
        private Boolean isCancelled;

        public static ToggleLike response(Review review, Users user, Boolean isCancelled) {
            return ToggleLike.builder()
                    .reviewId(review.getId())
                    .userId(user.getId())
                    .isCancelled(isCancelled)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class ReadDetails {
        private Reviews review;
        private Lectures lecture;
        private User user;

        public static ReadDetails response(Review review, Lecture lecture, Users users) {
            return ReadDetails.builder()
                    .review(Reviews.builder()
                            .reviewId(review.getId())
                            .score(review.getScore())
                            .content(review.getContent())
                            .createdDate(review.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .tags(review.getTags())
                            .likes(review.getLikeReviews().size())
                            .build()
                    )
                    .lecture(Lectures.builder()
                            .lectureId(lecture.getId())
                            .title(lecture.getTitle())
                            .imageUrl(lecture.getImageUrl())
                            .name(lecture.getName())
                            .build()
                    )
                    .user(User.builder()
                            .userId(users.getId())
                            .imageUrl(users.getImageUrl())
                            .nickName(users.getNickName())
                            .build()
                    )
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class ReadMyDetails {
        private Reviews review;
        private Lectures lecture;

        public static ReadMyDetails response(Review review, Lecture lecture) {
            return ReadMyDetails.builder()
                    .review(Reviews.builder()
                            .reviewId(review.getId())
                            .score(review.getScore())
                            .content(review.getContent())
                            .createdDate(review.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .tags(review.getTags())
                            .likes(review.getLikeReviews().size())
                            .build()
                    )
                    .lecture(Lectures.builder()
                            .lectureId(lecture.getId())
                            .title(lecture.getTitle())
                            .imageUrl(lecture.getImageUrl())
                            .name(lecture.getName())
                            .build()
                    )
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Reviews {
        private Long reviewId;
        private Double score;
        private String content;
        private String createdDate;
        private String tags;
        private Integer likes;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Lectures {
        private Long lectureId;
        private String title;
        private String imageUrl;
        private String name;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class User {
        private Long userId;
        private String imageUrl;
        private String nickName;
    }
}
