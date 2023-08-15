package dnd.project.domain.bookmark.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BookmarkResponse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Detail {
        private Long bookmarkId;
        private Long lectureId;
        private String lectureImageUrl;
        private String name;
        private String source;
        private String title;
        private String price;
        private String addedDate;
    }
}
