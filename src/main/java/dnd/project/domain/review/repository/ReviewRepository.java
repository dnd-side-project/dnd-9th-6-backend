package dnd.project.domain.review.repository;

import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.user.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(
            "SELECT r " +
            "FROM Review AS r " +
            "JOIN FETCH r.lecture " +
            "JOIN FETCH r.user " +
            "ORDER BY " +
            "r.createdDate DESC"
    )
    List<Review> findByRecentReview(Pageable pageable);
    Boolean existsByLectureAndUser(Lecture lecture, Users user);
}
