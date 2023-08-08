package dnd.project.domain.bookmark.service;

import dnd.project.domain.bookmark.entity.Bookmark;
import dnd.project.domain.bookmark.repository.BookmarkRepository;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.repository.LectureRepository;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import dnd.project.global.common.Result;
import dnd.project.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static dnd.project.global.common.Result.NOT_FOUND_LECTURE;
import static dnd.project.global.common.Result.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;
    private final BookmarkRepository bookmarkRepository;

    // 북마크 등록 API
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
