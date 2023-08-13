package dnd.project.domain.lecture.service;

import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.entity.LectureCategory;
import dnd.project.domain.lecture.repository.LectureQueryRepository;
import dnd.project.domain.lecture.response.LectureListReadResponse;
import dnd.project.domain.lecture.response.LectureScopeListReadResponse;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import dnd.project.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static dnd.project.global.common.Result.*;

@RequiredArgsConstructor
@Service
public class LectureService {
    private static final Integer MAX_SIZE = 100;

    private static final Integer DEFAULT_SIZE = 10;
    private static final Integer MIN_SIZE = 10;
    private final LectureQueryRepository lectureQueryRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public LectureListReadResponse getLectures(Integer mainCategoryId,
                                               Integer subCategoryId,
                                               String searchKeyword,
                                               Integer page,
                                               Integer size,
                                               String sort) {

        if (size == null) {
            size = DEFAULT_SIZE;
        } else if (size < MIN_SIZE) {
            size = MIN_SIZE;
        } else if (size > MAX_SIZE) {
            size = MAX_SIZE;
        }

        if (page == null) {
            page = 0;
        }

        if (mainCategoryId != null && subCategoryId != null) {
            // 메인 + 서브 카테고리 검색
            return getLecturesFromMainSubCategory(mainCategoryId, subCategoryId, searchKeyword, page, size, sort);
        } else if (mainCategoryId != null) {
            // 메인 카테고리 검색
            return getLecturesFromMainCategory(mainCategoryId, searchKeyword, page, size, sort);
        } else if (subCategoryId != null) {
            throw new CustomException(NOT_FOUND_MAIN_CATEGORY);
        } else {
            return getLecturesFromAllCategory(searchKeyword, page, size, sort);
        }
    }

    @Transactional(readOnly = true)
    public LectureScopeListReadResponse getScopeLectures(Long userId) {
        Users user = getUserOrAnonymous(userId);

        // 별점 높은 수강 후기들 -> 4.0 이상 랜덤
        List<LectureScopeListReadResponse.DetailReview> highScoreReviews =
                getHighScoreReviews(user.getInterests());

        // 강의력 좋은 순
        List<LectureScopeListReadResponse.DetailLecture> bestLectures =
                lectureQueryRepository.findByBestLectures(user.getInterests());

        return LectureScopeListReadResponse.response(highScoreReviews, bestLectures, user);
    }

    private LectureListReadResponse getLecturesFromMainSubCategory(Integer mainCategoryId,
                                                                   Integer subCategoryId,
                                                                   String searchKeyword,
                                                                   Integer page,
                                                                   Integer size,
                                                                   String sort) {

        LectureCategory category = findMainSubCategory(mainCategoryId, subCategoryId);
        String mainCategoryName = category.getMainCategoryName();
        String subCategoryName = category.getSubCategoryName();

        Page<Lecture> lectures = lectureQueryRepository.findAll(mainCategoryName, subCategoryName, searchKeyword, page, size, sort);

        List<Lecture> content = lectures.getContent();
        int totalPages = lectures.getTotalPages();
        long totalElements = lectures.getTotalElements();
        int pageSize = lectures.getPageable().getPageSize();
        int pageNumber = lectures.getPageable().getPageNumber();

        return LectureListReadResponse.of(totalPages, pageNumber, pageSize, totalElements, content);
    }

    private LectureListReadResponse getLecturesFromMainCategory(Integer mainCategoryId,
                                                                String searchKeyword,
                                                                Integer page,
                                                                Integer size,
                                                                String sort) {

        String mainCategoryName = findMainCategoryName(mainCategoryId);

        Page<Lecture> lectures = lectureQueryRepository.findAll(mainCategoryName, null, searchKeyword, page, size, sort);

        List<Lecture> content = lectures.getContent();
        int totalPages = lectures.getTotalPages();
        long totalElements = lectures.getTotalElements();
        int pageSize = lectures.getPageable().getPageSize();
        int pageNumber = lectures.getPageable().getPageNumber();

        return LectureListReadResponse.of(totalPages, pageNumber, pageSize, totalElements, content);
    }

    private LectureListReadResponse getLecturesFromAllCategory(String searchKeyword,
                                                               Integer page,
                                                               Integer size,
                                                               String sort) {

        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, searchKeyword, page, size, sort);

        List<Lecture> content = lectures.getContent();
        int totalPages = lectures.getTotalPages();
        long totalElements = lectures.getTotalElements();
        int pageSize = lectures.getPageable().getPageSize();
        int pageNumber = lectures.getPageable().getPageNumber();

        return LectureListReadResponse.of(totalPages, pageNumber, pageSize, totalElements, content);
    }

    private LectureCategory findMainSubCategory(Integer mainCategoryId, Integer subCategoryId) {
        return Arrays.stream(LectureCategory.values())
                .filter(lectureCategory -> lectureCategory.getMainCategoryId().equals(mainCategoryId))
                .filter(lectureCategory -> lectureCategory.getSubCategoryId().equals(subCategoryId))
                .findAny()
                .orElseThrow(() -> new CustomException(NOT_FOUND_MAIN_AND_SUB_CATEGORY));
    }

    private String findMainCategoryName(Integer mainCategoryId) {
        LectureCategory category = Arrays.stream(LectureCategory.values())
                .filter(lectureCategory -> lectureCategory.getMainCategoryId().equals(mainCategoryId))
                .findAny()
                .orElseThrow(() -> new CustomException(NOT_FOUND_MAIN_CATEGORY));
        return category.getMainCategoryName();
    }

    private List<LectureScopeListReadResponse.DetailReview> getHighScoreReviews(String interests) {
        return lectureQueryRepository.findByHighScores(interests).stream()
                .map(review -> LectureScopeListReadResponse.DetailReview.toEntity(
                        review, review.getUser(), review.getLecture())
                ).toList();
    }

    private Users getUserOrAnonymous(Long userId) {
        if (userId == null) {
            return Users.builder().build();
        }
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
    }
}
