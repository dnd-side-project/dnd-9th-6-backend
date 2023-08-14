package dnd.project.domain.lecture.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class LectureReviewListReadResponse {

    private final Integer totalPages;
    private final Integer pageNumber;
    private final Integer pageSize;
    private final Long totalElements;
    private final List<ReviewInfo> reviews;

    @Getter
    public static class ReviewInfo {
        private final Long id;
        private final String nickname;
        private final List<String> tags = new ArrayList<>();
        private final String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private final LocalDateTime createdDate;
        private final Double score;
        private final Long likeCount;

        public ReviewInfo(Long id, String nickname, String tags, String content, LocalDateTime createdDate, Double score, Long likeCount) {
            this.id = id;
            this.nickname = nickname;
            for (String tag : tags.split(",")) {
                this.tags.add(tag.trim());
            }
            this.content = content;
            this.createdDate = createdDate;
            this.score = score;
            this.likeCount = likeCount;
        }
    }

    public static LectureReviewListReadResponse of(Integer totalPages,
                                                   Integer pageNumber,
                                                   Integer pageSize,
                                                   Long totalElements,
                                                   List<ReviewInfo> reviewInfos) {
        return new LectureReviewListReadResponse(totalPages,
                pageNumber,
                pageSize,
                totalElements,
                reviewInfos);
    }
}
