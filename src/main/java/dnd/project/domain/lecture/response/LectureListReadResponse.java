package dnd.project.domain.lecture.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dnd.project.domain.lecture.entity.Lecture;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class LectureListReadResponse {

    private final Integer totalPages;
    private final Integer pageNumber;
    private final Integer pageSize;
    private final Long totalElements;
    private final List<LectureInfo> lectures;

    @Getter
    @RequiredArgsConstructor
    public static class LectureInfo {
        private final Long id;
        private final String title;
        private final String source;
        private final String url;
        private final String price;
        private final String name;
        private final String mainCategory;
        private final String subCategory;
        private final String imageUrl;
        private final Long reviewCount;
        private final Long bookmarkCount;
        private final Double averageScore;
        private final List<TagGroup> tagGroups;
        private final List<ReviewInfo> reviews; // 최근 10개 리뷰

        public static LectureInfo of(Lecture lecture,
                                     Long reviewCount,
                                     Long bookmarkCount,
                                     Double averageScore,
                                     List<TagGroup> tagGroups,
                                     List<ReviewInfo> reviews) {
            return new LectureInfo(lecture.getId(),
                    lecture.getTitle(),
                    lecture.getSource(),
                    lecture.getUrl(),
                    lecture.getFormattedPrice(),
                    lecture.getName(),
                    lecture.getMainCategory(),
                    lecture.getSubCategory(),
                    lecture.getImageUrl(),
                    reviewCount,
                    bookmarkCount,
                    averageScore,
                    tagGroups,
                    reviews);
        }

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
    }

    public static LectureListReadResponse of(Integer totalPages,
                                             Integer pageNumber,
                                             Integer pageSize,
                                             Long totalElements,
                                             List<LectureInfo> lectures) {
        return new LectureListReadResponse(totalPages,
                pageNumber,
                pageSize,
                totalElements,
                lectures);
    }
}
