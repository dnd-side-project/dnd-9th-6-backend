package dnd.project.domain.lecture.controller;

import dnd.project.domain.lecture.response.LectureListReadResponse;
import dnd.project.domain.lecture.service.LectureService;
import dnd.project.global.common.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LectureController {

    private final LectureService lectureService;

    @GetMapping("/lectures")
    public CustomResponseEntity<LectureListReadResponse> getLectures(@RequestParam(required = false) Integer mainCategoryId,
                                                                     @RequestParam(required = false) Integer subCategoryId,
                                                                     @RequestParam(required = false) String searchKeyword,
                                                                     @RequestParam(required = false) Integer page,
                                                                     @RequestParam(required = false) Integer size,
                                                                     @RequestParam(required = false) String sort) {

        return CustomResponseEntity.success(lectureService.getLectures(mainCategoryId,
                subCategoryId,
                searchKeyword,
                page,
                size,
                sort));
    }
}
