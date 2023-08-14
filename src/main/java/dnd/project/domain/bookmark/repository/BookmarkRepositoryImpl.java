package dnd.project.domain.bookmark.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dnd.project.domain.bookmark.response.BookmarkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.stringTemplate;
import static dnd.project.domain.bookmark.entity.QBookmark.bookmark;
import static dnd.project.domain.lecture.entity.QLecture.lecture;
import static dnd.project.domain.user.entity.QUsers.users;

@Repository
@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookmarkResponse.Detail> findByMyBookmark(Long userId) {
        return queryFactory
                .select(Projections.fields(BookmarkResponse.Detail.class,
                        bookmark.id.as("bookmarkId"),
                        bookmark.lecture.id.as("lectureId"),
                        bookmark.lecture.name.as("name"),
                        bookmark.lecture.imageUrl.as("lectureImageUrl"),
                        bookmark.lecture.source.as("source"),
                        bookmark.lecture.title.as("title"),
                        bookmark.lecture.price.as("price"),
                        stringTemplate("TO_CHAR({0}, 'yyyy-MM-dd')", bookmark.createdDate).as("addedDate")))
                .from(bookmark)
                .innerJoin(bookmark.lecture, lecture)
                .innerJoin(bookmark.user, users)
                .where(bookmark.user.id.eq(userId))
                .orderBy(bookmark.createdDate.desc())
                .fetch();
    }
}
