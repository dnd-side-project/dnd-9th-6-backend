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

    @Override
    public List<LectureScopeListReadResponse.DetailReview> findByKeyword(String keyword) {
        return queryFactory
                .select(Projections.fields(LectureScopeListReadResponse.DetailReview.class,
                        review.id.as("id"),
                        review.lecture.title.as("lectureTitle"),
                        review.user.nickName.as("userName"),
                        Expressions.stringTemplate("TO_CHAR({0}, 'yyyy-MM-dd')", review.createdDate).as("createdDate"), // 변경된 부분
                        review.score.as("score"),
                        review.content.as("content"),
                        review.tags.as("tags"),
                        review.lecture.source.as("source")
                        ))
                .from(review)
                .innerJoin(review.lecture,lecture)
                .innerJoin(review.user, users)
                .where(review.tags.contains(keyword))
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(10)
                .fetch();
    }
}
