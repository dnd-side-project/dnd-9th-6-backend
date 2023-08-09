package dnd.project.domain.review.repository;

import dnd.project.domain.review.entity.LikeReview;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeReviewRepository extends JpaRepository<LikeReview, Long> {
    void deleteByReviewAndUsers(Review review, Users user);
}
