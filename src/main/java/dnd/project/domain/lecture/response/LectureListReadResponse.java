package dnd.project.domain.lecture.response;

import dnd.project.domain.lecture.entity.Lecture;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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

        public static LectureInfo from(Lecture lecture) {
            return new LectureInfo(lecture.getId(),
                    lecture.getTitle(),
                    lecture.getSource(),
                    lecture.getUrl(),
                    lecture.getPrice(),
                    lecture.getName(),
                    lecture.getMainCategory(),
                    lecture.getSubCategory(),
                    lecture.getImageUrl());
        }
    }

    public static LectureListReadResponse of(Integer totalPages,
                                             Integer pageNumber,
                                             Integer pageSize,
                                             Long totalElements,
                                             List<Lecture> lectures) {
        return new LectureListReadResponse(totalPages,
                pageNumber,
                pageSize,
                totalElements,
                lectures.stream()
                        .map(LectureInfo::from)
                        .collect(Collectors.toList()));
    }
}
