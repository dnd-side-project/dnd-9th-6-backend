package dnd.project.domain.lecture.controller;

import dnd.project.domain.lecture.response.LectureListReadResponse;
import dnd.project.domain.lecture.response.LectureReadResponse;
import dnd.project.domain.lecture.response.LectureReviewListReadResponse;
import dnd.project.domain.lecture.response.LectureScopeListReadResponse;
import dnd.project.domain.lecture.service.LectureService;
import dnd.project.global.common.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    // 강의 상세 조회 API
    @GetMapping("/lectures/{id}")
    public CustomResponseEntity<LectureReadResponse> getLecture(@PathVariable Long id) {
        return CustomResponseEntity.success(lectureService.getLecture(id));
    }

    // 강의 리뷰 조회 API
    @GetMapping("/lectures/{id}/reviews")
    public CustomResponseEntity<LectureReviewListReadResponse> getLectureReviews(@PathVariable Long id,
                                                                                 @RequestParam(required = false) String searchKeyword,
                                                                                 @RequestParam(required = false) Integer page,
                                                                                 @RequestParam(required = false) Integer size,
                                                                                 @RequestParam(required = false) String sort) {

        return CustomResponseEntity.success(lectureService.getLectureReviews(id,
                searchKeyword,
                page,
                size,
                sort));
    }

    // 별점 높은 수강 후기들 조회 API
    @GetMapping("/lectures/scope/reviews")
    public CustomResponseEntity<List<LectureScopeListReadResponse.DetailReview>> getScopeReviewsScore(
            @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(lectureService.getScopeReviewsScore(userId));
    }

    // 강의력 좋은 강의 조회 API
    @GetMapping("/lectures/scope/lectures")
    public CustomResponseEntity<List<LectureScopeListReadResponse.DetailLecture>> getScopeLecturesBest(
            @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(lectureService.getScopeLecturesBest(userId));
    }

}
