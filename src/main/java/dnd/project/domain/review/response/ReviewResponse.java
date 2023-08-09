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
        private Integer score;
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
}
