package dnd.project.domain.bookmark.repository;

import dnd.project.domain.TestQuerydslConfig;
import dnd.project.domain.bookmark.entity.Bookmark;
import dnd.project.domain.bookmark.response.BookmarkResponse;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.repository.LectureRepository;
import dnd.project.domain.user.entity.Authority;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static dnd.project.domain.user.entity.Authority.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestQuerydslConfig.class)
class BookmarkRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookmarkRepository bookmarkRepository;

    @DisplayName("자신이 북마크해둔 강의들을 전부 조회한다.")
    @Test
    void findByMyBookmark() {
        // given
        Lecture lecture1 = toEntityLecture("실용적인 테스트 가이드1", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Lecture lecture2 = toEntityLecture("실용적인 테스트 가이드2", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Lecture lecture3 = toEntityLecture("실용적인 테스트 가이드3", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Lecture lecture4 = toEntityLecture("실용적인 테스트 가이드4", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        Lecture lecture5 = toEntityLecture("실용적인 테스트 가이드5", "프로그래밍", "백엔드", "테스트,백엔드,스프링,spring");
        List<Lecture> lectures = lectureRepository.saveAll(List.of(lecture1, lecture2, lecture3, lecture4, lecture5));

        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);

        Bookmark bookmark1 = toEntityBookmark(user, lecture1);
        Bookmark bookmark2 = toEntityBookmark(user, lecture3);
        Bookmark bookmark3 = toEntityBookmark(user, lecture5);
        List<Bookmark> bookmarks = bookmarkRepository.saveAll(List.of(bookmark1, bookmark2, bookmark3));

        entityManager.flush();
        entityManager.clear();

        // when
        List<BookmarkResponse.Detail> myBookmarks = bookmarkRepository.findByMyBookmark(user.getId());

        // then
        assertThat(myBookmarks)
                .extracting("lectureId","bookmarkId","title")
                .contains(
                        tuple(lectures.get(4).getId(), bookmarks.get(2).getId(), lectures.get(4).getTitle()),
                        tuple(lectures.get(2).getId(), bookmarks.get(1).getId(), lectures.get(2).getTitle()),
                        tuple(lectures.get(0).getId(), bookmarks.get(0).getId(), lectures.get(0).getTitle())
                );
    }

    @DisplayName("자신이 북마크해둔 강의들을 전부 조회할때 아직 등록해둔 북마크가 없다.")
    @Test
    void findByMyBookmarkWithEmptyBookmarks() {
        // given
        Users user = saveUser("test@test.com", "test", "test", ROLE_USER);

        // when
        List<BookmarkResponse.Detail> myBookmarks = bookmarkRepository.findByMyBookmark(user.getId());

        // then
        assertThat(myBookmarks.isEmpty()).isTrue();
    }

    // method

    private Bookmark toEntityBookmark(Users user, Lecture lecture) {
        return Bookmark.builder()
                .user(user)
                .lecture(lecture)
                .build();
    }

    private Lecture saveLecture(String title, String mainCategory, String subCategory, String keywords) {
        return lectureRepository.save(
                toEntityLecture(title, mainCategory, subCategory, keywords)
        );
    }

    private static Lecture toEntityLecture(String title, String mainCategory, String subCategory, String keywords) {
        return Lecture.builder()
                .title(title)
                .source("인프런")
                .url("https://www.inflearn.com/course/practical-testing-실용적인-테스트-가이드/dashboard")
                .price("53900")
                .name("박우빈")
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .keywords(keywords)
                .imageUrl("test")
                .build();
    }

    private Users saveUser(String email, String password, String nickName, Authority authority) {
        return userRepository.save(
                Users.builder()
                        .email(email)
                        .password(password)
                        .nickName(nickName)
                        .authority(authority)
                        .build()
        );
    }
}