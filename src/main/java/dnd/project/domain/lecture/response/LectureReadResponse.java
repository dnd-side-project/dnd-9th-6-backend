package dnd.project.domain.lecture.response;

import dnd.project.domain.lecture.entity.Lecture;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class LectureReadResponse {

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
    private final Double averageScore;
    private final List<TagGroup> tagGroups;

    public static LectureReadResponse of(Lecture lecture,
                                         Long reviewCount,
                                         Double averageScore,
                                         List<TagGroup> tagGroups) {

        return new LectureReadResponse(lecture.getId(),
                lecture.getTitle(),
                lecture.getSource(),
                lecture.getUrl(),
                lecture.getFormattedPrice(),
                lecture.getName(),
                lecture.getMainCategory(),
                lecture.getSubCategory(),
                lecture.getImageUrl(),
                reviewCount,
                averageScore,
                tagGroups);
    }
}
