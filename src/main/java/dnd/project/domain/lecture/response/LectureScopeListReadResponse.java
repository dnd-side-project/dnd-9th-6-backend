package dnd.project.domain.lecture.response;

import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.user.entity.Users;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LectureScopeListReadResponse {

    private Boolean isAnonymous;
    private String userName;
    private String interests;
    private List<DetailReview> highScoreReviews;
    private List<DetailLecture> bestLectures;

    public static LectureScopeListReadResponse response(
            List<DetailReview> highScoreReviews, List<DetailLecture> bestLectures, Users user
    ) {
        boolean isAnonymous = user.getNickName() == null;
        String userName = user.getNickName() == null ? "anonymous" : user.getNickName();
        String interests = (user.getInterests() == null) ? "anonymous" : user.getInterests();

        return LectureScopeListReadResponse.builder()
                .isAnonymous(isAnonymous)
                .userName(userName)
                .interests(interests)
                .highScoreReviews(highScoreReviews)
                .bestLectures(bestLectures)
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class DetailLecture {
        private Long id;
        private String imageUrl;
        private String title;
        private String name;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class DetailReview {
        private Long id;
        private String lectureTitle;
        private String imageUrl;
        private String userName;
        private String createdDate;
        private Double score;
        private String content;
        private String tags;

        public static DetailReview toEntity(Review review, Users user, Lecture lecture) {
            return DetailReview.builder()
                    .id(review.getId())
                    .lectureTitle(lecture.getTitle())
                    .imageUrl(user.getImageUrl())
                    .userName(user.getNickName())
                    .createdDate(review.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .score(review.getScore())
                    .content(review.getContent())
                    .tags(review.getTags())
                    .build();
        }
    }
}
