package dnd.project.domain.lecture.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class LectureScopeListReadResponse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class DetailLecture {
        private Long id;
        private String source;
        private String imageUrl;
        private String title;
        private String name;
        private String url;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class DetailReview {
        private Long id;
        private String lectureTitle;
        private String userName;
        private Double score;
        private String content;
        private String tags;
        private String source;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime createdDate;
    }
}
