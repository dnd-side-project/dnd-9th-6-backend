package dnd.project.domain.lecture.response;

import lombok.*;

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
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class DetailReview {
        private Long id;
        private String lectureTitle;
        private String userName;
        private String createdDate;
        private Double score;
        private String content;
        private String tags;
        private String source;
    }
}
