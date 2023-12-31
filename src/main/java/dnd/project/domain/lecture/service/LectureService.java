package dnd.project.domain.lecture.service;

import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.entity.LectureCategory;
import dnd.project.domain.lecture.repository.LectureQueryRepository;
import dnd.project.domain.lecture.repository.LectureRepository;
import dnd.project.domain.lecture.response.*;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.review.entity.ReviewTag;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import dnd.project.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static dnd.project.global.common.Result.*;

@RequiredArgsConstructor
@Service
public class LectureService {
    private static final Integer LECTURE_MAX_SIZE = 100;
    private static final Integer LECTURE_DEFAULT_SIZE = 10;
    private static final Integer LECTURE_MIN_SIZE = 10;
    private static final Integer LECTURE_REVIEW_MAX_SIZE = 10;
    private static final Integer LECTURE_REVIEW_DEFAULT_SIZE = 5;
    private static final Integer LECTURE_REVIEW_MIN_SIZE = 1;
    private static final Set<String> TAG_TYPES = Arrays.stream(ReviewTag.values())
            .map(ReviewTag::getType)
            .collect(Collectors.toSet());
    private static final List<String> TAG_NAMES = Arrays.stream(ReviewTag.values())
            .map(ReviewTag::getName)
            .toList();
    private static final Map<String, String> TAG_NAME_TYPES = Arrays.stream(ReviewTag.values()).
            collect(Collectors.toMap(ReviewTag::getName,
                    ReviewTag::getType));

    private final LectureQueryRepository lectureQueryRepository;
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public LectureReadResponse getLecture(Long id) {

        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_LECTURE));

        Long reviewCount = lectureQueryRepository.findReviewCountById(id);

        Double averageScore = lectureQueryRepository.findReviewAverageScoreById(id);
        Double roundedAverageScore = Math.round(averageScore * 2) / 2.0;

        // 전체 태그를 "," 로 구분 하여 자르고 각 키워드 별 카운트
        List<String> tagsList = lectureQueryRepository.findAllReviewTagsById(id);

        List<TagGroup> tagGroups = getTagGroups(tagsList);

        return LectureReadResponse.of(lecture, reviewCount, roundedAverageScore, tagGroups);
    }

    private List<TagGroup> getTagGroups(List<String> tagsList) {

        // 카운트 0 초기화
        Map<String, Integer> tagCountMap = new HashMap<>();
        for (String tagName : TAG_NAMES) {
            tagCountMap.put(tagName, 0);
        }

        for (String tags : tagsList) {
            for (String tag : tags.split(",")) {
                tag = tag.trim();
                Integer count = tagCountMap.getOrDefault(tag, 0);
                tagCountMap.put(tag, count + 1);
            }
        }

        Map<String, List<TagGroup.Tag>> result = new HashMap<>();

        for (Map.Entry<String, Integer> entry : tagCountMap.entrySet()) {
            String tagName = entry.getKey();
            Integer tagCount = entry.getValue();
            String tagType = TAG_NAME_TYPES.get(tagName);

            result.computeIfAbsent(tagType, k -> new ArrayList<>())
                    .add(new TagGroup.Tag(tagName, tagCount));
        }

        return result.entrySet().stream()
                .map(response -> new TagGroup(response.getKey(), response.getValue()))
                .toList();
    }

    @Transactional(readOnly = true)
    public LectureReviewListReadResponse getLectureReviews(Long id,
                                                           String searchKeyword,
                                                           Integer page,
                                                           Integer size,
                                                           String sort) {

        if (size == null) {
            size = LECTURE_REVIEW_DEFAULT_SIZE;
        } else if (size < LECTURE_REVIEW_MIN_SIZE) {
            size = LECTURE_REVIEW_MIN_SIZE;
        } else if (size > LECTURE_REVIEW_MAX_SIZE) {
            size = LECTURE_REVIEW_MAX_SIZE;
        }

        if (page == null) {
            page = 0;
        }

        Page<LectureReviewListReadResponse.ReviewInfo> reviews = lectureQueryRepository.findAllReviewsById(id, searchKeyword, page, size, sort);

        List<LectureReviewListReadResponse.ReviewInfo> content = reviews.getContent();
        int totalPages = reviews.getTotalPages();
        long totalElements = reviews.getTotalElements();
        int pageSize = reviews.getPageable().getPageSize();
        int pageNumber = reviews.getPageable().getPageNumber();

        return LectureReviewListReadResponse.of(totalPages, pageNumber, pageSize, totalElements, content);
    }

    @Transactional(readOnly = true)
    public LectureListReadResponse getLectures(Integer mainCategoryId,
                                               Integer subCategoryId,
                                               String searchKeyword,
                                               Integer page,
                                               Integer size,
                                               String sort) {

        if (size == null) {
            size = LECTURE_DEFAULT_SIZE;
        } else if (size < LECTURE_MIN_SIZE) {
            size = LECTURE_MIN_SIZE;
        } else if (size > LECTURE_MAX_SIZE) {
            size = LECTURE_MAX_SIZE;
        }

        if (page == null) {
            page = 0;
        }

        if (mainCategoryId != null && subCategoryId != null) {

            if (mainCategoryId == 0 && subCategoryId == 0) {
                return getLecturesFromAllCategory(searchKeyword, page, size, sort);
            }

            // 메인 + 서브 카테고리 검색
            return getLecturesFromMainSubCategory(mainCategoryId, subCategoryId, searchKeyword, page, size, sort);
        } else if (mainCategoryId != null) {

            if (mainCategoryId == 0) {
                return getLecturesFromAllCategory(searchKeyword, page, size, sort);
            }

            // 메인 카테고리 검색
            return getLecturesFromMainCategory(mainCategoryId, searchKeyword, page, size, sort);
        } else if (subCategoryId != null) {
            throw new CustomException(NOT_FOUND_MAIN_CATEGORY);
        } else {
            return getLecturesFromAllCategory(searchKeyword, page, size, sort);
        }
    }

    // Scope 메인 페이지 조회 API - 별점 높은 수강 후기순
    @Transactional(readOnly = true)
    public List<LectureScopeListReadResponse.DetailReview> getScopeReviewsScore(Long userId) {
        Users user = getUserOrAnonymous(userId);
        return lectureQueryRepository.findByHighScores(user.getInterests());
    }

    // Scope 메인 페이지 조회 API - 강의력 좋은순
    @Transactional(readOnly = true)
    public List<LectureScopeListReadResponse.DetailLecture> getScopeLecturesBest(Long userId) {
        Users user = getUserOrAnonymous(userId);
        return lectureQueryRepository.findByBestLectures(user.getInterests());
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

        List<LectureListReadResponse.LectureInfo> content = getLectureInfos(lectures);

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

        List<LectureListReadResponse.LectureInfo> content = getLectureInfos(lectures);

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

        List<LectureListReadResponse.LectureInfo> content = getLectureInfos(lectures);

        int totalPages = lectures.getTotalPages();
        long totalElements = lectures.getTotalElements();
        int pageSize = lectures.getPageable().getPageSize();
        int pageNumber = lectures.getPageable().getPageNumber();

        return LectureListReadResponse.of(totalPages, pageNumber, pageSize, totalElements, content);
    }

    private List<LectureListReadResponse.LectureInfo> getLectureInfos(Page<Lecture> lectures) {
        List<Long> ids = lectures.getContent().stream()
                .map(Lecture::getId)
                .toList();

        Map<Long, Long> reviewCount = lectureQueryRepository.findReviewCount(ids);
        Map<Long, Long> bookmarkCount = lectureQueryRepository.findBookmarkCount(ids);
        Map<Long, Double> reviewAverageScore = lectureQueryRepository.findReviewAverageScore(ids);
        Map<Object, List<Review>> reviewsByLectureId = lectureQueryRepository.findAllReviewsByIds(ids)
                .stream()
                .collect(Collectors.groupingBy(review -> review.getLecture().getId()));

        return lectures.getContent().stream()
                .map(lecture -> {

                    List<String> tags = reviewsByLectureId.getOrDefault(lecture.getId(), Collections.emptyList())
                            .stream()
                            .map(Review::getTags)
                            .toList();

                    List<TagGroup> tagGroups = getTagGroups(tags);

                    Double averageScore = reviewAverageScore.getOrDefault(lecture.getId(), 0.0);
                    if (averageScore == null) {
                        averageScore = 0.0;
                    }

                    Double roundedAverageScore = Math.round(averageScore * 2) / 2.0;

                    return LectureListReadResponse.LectureInfo.of(
                            lecture,
                            reviewCount.getOrDefault(lecture.getId(), 0L),
                            bookmarkCount.getOrDefault(lecture.getId(), 0L),
                            roundedAverageScore,
                            tagGroups,
                            lectureQueryRepository.findAllReviewsById(lecture.getId()));
                })
                .collect(Collectors.toList());
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

    private Users getUserOrAnonymous(Long userId) {
        if (userId == null) {
            return Users.builder().build();
        }
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
    }

}
