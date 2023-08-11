package dnd.project.domain.lecture.request;

import lombok.Data;

@Data
public class LectureListReadRequest {
    private Integer mainCategoryId;
    private Integer subCategoryId;
    private String searchKeyword;
}
