package dnd.project.domain.bookmark.repository;

import dnd.project.domain.bookmark.response.BookmarkResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkCustomRepository {
    List<BookmarkResponse.Detail> findByMyBookmark(Long userId);
}
