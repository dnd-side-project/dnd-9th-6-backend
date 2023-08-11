package dnd.project.domain.review.repository;

import dnd.project.domain.review.entity.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewQueryRepository {
    List<Review> findByRecentReview();
    List<Review> findByMyReview(Long userId);
}
