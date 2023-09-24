package dnd.project.domain.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dnd.project.domain.lecture.response.LectureScopeListReadResponse;
import dnd.project.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static dnd.project.domain.lecture.entity.QLecture.lecture;
import static dnd.project.domain.review.entity.QLikeReview.likeReview;
import static dnd.project.domain.review.entity.QReview.review;
import static dnd.project.domain.user.entity.QUsers.users;

@Repository
@RequiredArgsConstructor

public class ReviewRepositoryImpl implements ReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Review> findByRecentReview() {
        return queryFactory
                .selectFrom(review)
                .innerJoin(review.lecture, lecture).fetchJoin()
                .leftJoin(review.user, users).fetchJoin()
                .orderBy(review.createdDate.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<Review> findByMyReview(Long userId) {
        return queryFactory
                .selectFrom(review)
                .innerJoin(review.lecture, lecture).fetchJoin()
                .leftJoin(review.likeReviews, likeReview).fetchJoin()
                .where(review.user.id.eq(userId))
                .fetch();
    }
}
