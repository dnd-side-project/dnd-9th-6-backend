package dnd.project.domain.bookmark.repository;

import dnd.project.domain.bookmark.entity.Bookmark;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByLectureAndUser(Lecture lecture, Users user);
    void deleteByLectureAndUser(Lecture lecture, Users users);
}
