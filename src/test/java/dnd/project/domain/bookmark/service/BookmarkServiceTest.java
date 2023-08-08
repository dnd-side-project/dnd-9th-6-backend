package dnd.project.domain.bookmark.service;

import dnd.project.domain.bookmark.entity.Bookmark;
import dnd.project.domain.bookmark.repository.BookmarkRepository;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.repository.LectureRepository;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static dnd.project.domain.user.entity.Authority.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BookmarkServiceTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BookmarkService bookmarkService;

    @DisplayName("유저가 하나의 강의를 북마크 한다.")
    @Test
    void addBookmark() {
        // given
        Lecture lecture = lectureRepository.save(
                Lecture.builder()
                        .title("Practical Testing: 실용적인 테스트 가이드")
                        .source("인프런")
                        .url("https://www.inflearn.com/course/practical-testing-실용적인-테스트-가이드/dashboard")
                        .price("53900")
                        .name("박우빈")
                        .mainCategory("프로그래밍")
                        .subCategory("백엔드")
                        .keywords("테스트,test,백엔드,스프링,스프링부트,spring")
                        .imageUrl("test")
                        .build()
        );

        Users user = userRepository.save(
                Users.builder()
                        .email("test@test.com")
                        .password("test")
                        .nickName("test")
                        .authority(ROLE_USER)
                        .build()
        );

        // when
        bookmarkService.addBookmark(lecture.getId(), user.getId());

        entityManager.flush();
        entityManager.clear();

        // then
        Optional<Bookmark> validateLecture = bookmarkRepository.findByLectureAndUser(lecture, user);
        assertThat(validateLecture.isPresent()).isTrue();
    }
}