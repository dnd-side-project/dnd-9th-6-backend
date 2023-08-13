package dnd.project.domain.lecture.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dnd.project.domain.review.entity.Review;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class LectureReviewListReadResponse {

    private final Integer totalPages;
    private final Integer pageNumber;
    private final Integer pageSize;
    private final Long totalElements;
    private final List<ReviewInfo> reviews;

    @Getter
    @RequiredArgsConstructor
    public static class ReviewInfo {
        private final Long id;
        private final String nickname;
        private final List<String> tags;
        private final String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private final LocalDateTime createdDate;

        public static ReviewInfo from(Review review) {
            return new ReviewInfo(review.getId(),
                    review.getUser().getNickName(),
                    Arrays.stream(review.getTags().split(",")).toList(),
                    review.getContent(),
                    review.getCreatedDate());
        }
    }

    public static LectureReviewListReadResponse of(Integer totalPages,
                                                   Integer pageNumber,
                                                   Integer pageSize,
                                                   Long totalElements,
                                                   List<Review> reviews) {
        return new LectureReviewListReadResponse(totalPages,
                pageNumber,
                pageSize,
                totalElements,
                reviews.stream()
                        .map(LectureReviewListReadResponse.ReviewInfo::from)
                        .collect(Collectors.toList()));
    }
}
