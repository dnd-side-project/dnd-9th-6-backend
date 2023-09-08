package dnd.project.domain.lecture.response;

import dnd.project.domain.lecture.entity.Lecture;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

        public static LectureInfo of(Lecture lecture, Long reviewCount, Long bookmarkCount) {
            return new LectureInfo(lecture.getId(),
                    lecture.getTitle(),
                    lecture.getSource(),
                    lecture.getUrl(),
                    lecture.getPrice(),
                    lecture.getName(),
                    lecture.getMainCategory(),
                    lecture.getSubCategory(),
                    lecture.getImageUrl(),
                    reviewCount,
                    bookmarkCount);
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
