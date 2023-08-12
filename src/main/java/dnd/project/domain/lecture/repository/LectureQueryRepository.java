package dnd.project.domain.lecture.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dnd.project.domain.lecture.entity.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static dnd.project.domain.bookmark.entity.QBookmark.bookmark;
import static dnd.project.domain.lecture.entity.QLecture.lecture;
import static dnd.project.domain.review.entity.QReview.review;

@RequiredArgsConstructor
@Repository
public class LectureQueryRepository {


    private final JPAQueryFactory queryFactory;

    public Page<Lecture> findAll(String mainCategory,
                                 String subCategory,
                                 String searchKeyword,
                                 Integer page,
                                 Integer size,
                                 String sort) {

        List<Lecture> content;

        if ("review,asc".equals(sort) || "review,desc".equals(sort)) {
            content = queryFactory.select(lecture)
                    .from(lecture)
                    .leftJoin(review).on(lecture.id.eq(review.lecture.id))
                    .where(equalsMainCategory(mainCategory),
                            equalsSubCategory(subCategory),
                            likeSearchKeyword(searchKeyword))
                    .groupBy(lecture.id)
                    .orderBy(sort(sort), defaultSort())
                    .offset(page)
                    .limit(size)
                    .fetch();

        } else if ("bookmark,asc".equals(sort) || "bookmark,desc".equals(sort)) {
            content = queryFactory.select(lecture)
                    .from(lecture)
                    .leftJoin(bookmark).on(lecture.id.eq(bookmark.lecture.id))
                    .where(equalsMainCategory(mainCategory),
                            equalsSubCategory(subCategory),
                            likeSearchKeyword(searchKeyword))
                    .groupBy(lecture.id)
                    .orderBy(sort(sort), defaultSort())
                    .offset(page)
                    .limit(size)
                    .fetch();
        } else {
            content = queryFactory.selectFrom(lecture)
                    .where(equalsMainCategory(mainCategory),
                            equalsSubCategory(subCategory),
                            likeSearchKeyword(searchKeyword))
                    .orderBy(sort(sort), defaultSort())
                    .offset(page)
                    .limit(size)
                    .fetch();
        }


        Long totalCount = queryFactory.select(lecture.count())
                .from(lecture)
                .where(equalsMainCategory(mainCategory),
                        equalsSubCategory(subCategory),
                        likeSearchKeyword(searchKeyword))
                .fetchOne();

        if (totalCount == null) {
            totalCount = 0L;
        }

        return new PageImpl<>(content,
                PageRequest.of(page, size),
                totalCount);
    }

    private BooleanExpression equalsMainCategory(String mainCategory) {
        if (!StringUtils.hasText(mainCategory)) {
            return null;
        }
        return lecture.mainCategory.eq(mainCategory);
    }

    private BooleanExpression equalsSubCategory(String subCategory) {
        if (!StringUtils.hasText(subCategory)) {
            return null;
        }
        return lecture.subCategory.eq(subCategory);
    }

    private BooleanBuilder likeSearchKeyword(String searchKeyword) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (StringUtils.hasText(searchKeyword)) {
            booleanBuilder.or(lecture.title.contains(searchKeyword));
            booleanBuilder.or(lecture.name.contains(searchKeyword));
            booleanBuilder.or(lecture.keywords.contains(searchKeyword));
            booleanBuilder.or(lecture.content.contains(searchKeyword));
        }

        return booleanBuilder;
    }

    private OrderSpecifier<?> sort(String sort) {

        if (!StringUtils.hasText(sort)) {
            return defaultSort();
        }

        return switch (sort) {
            case "price,asc" -> lecture.price.castToNum(Integer.class).asc();
            case "price,desc" -> lecture.price.castToNum(Integer.class).desc();
            case "title,asc" -> lecture.title.asc();
            case "title,desc" -> lecture.title.desc();
            case "name,asc" -> lecture.name.asc();
            case "name,desc" -> lecture.name.desc();
            case "bookmark,asc" -> bookmark.id.count().asc();
            case "bookmark,desc" -> bookmark.id.count().desc();
            case "review,asc" -> review.id.count().asc();
            case "review,desc" -> review.id.count().desc();
            default -> defaultSort();
        };
    }

    private OrderSpecifier<Long> defaultSort() {
        return lecture.id.asc();
    }
}
