package dnd.project.domain.bookmark.service;

import dnd.project.domain.bookmark.entity.Bookmark;
import dnd.project.domain.bookmark.repository.BookmarkRepository;
import dnd.project.domain.bookmark.response.BookmarkResponse;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.repository.LectureRepository;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import dnd.project.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dnd.project.global.common.Result.NOT_FOUND_LECTURE;
import static dnd.project.global.common.Result.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;
    private final BookmarkRepository bookmarkRepository;

    // 북마크 등록 API
    @Transactional
    public Void addBookmark(Long lectureId, Long userId) {
        Users user = getUser(userId);
        Lecture lecture = getLecture(lectureId);

        bookmarkRepository.save(
                Bookmark.builder()
                        .user(user)
                        .lecture(lecture)
                        .build()
        );
        return null;
    }

    // 북마크 취소 API
    @Transactional
    public Void cancelBookmark(Long lectureId, Long userId) {
        Users users = getUser(userId);
        Lecture lecture = getLecture(lectureId);

        bookmarkRepository.deleteByLectureAndUser(lecture, users);
        return null;
    }

    // 내 북마크 조회 API
    public List<BookmarkResponse.Detail> readMyBookmark(Long userId) {
        return bookmarkRepository.findByMyBookmark(userId);
    }

    // method

    private Lecture getLecture(Long lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow(
                () -> new CustomException(NOT_FOUND_LECTURE)
        );
    }

    private Users getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
    }
}
