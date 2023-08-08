package dnd.project.domain.bookmark.controller;

import dnd.project.domain.bookmark.service.BookmarkService;
import dnd.project.global.common.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // 북마크 등록 API
    @PostMapping("/bookmark")
    public CustomResponseEntity<Void> addBookmark(
            @RequestParam Long lectureId, @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(bookmarkService.addBookmark(lectureId, userId));
    }

    // 북마크 취소 API
    @DeleteMapping("/bookmark")
    public CustomResponseEntity<Void> cancelBookmark(
            @RequestParam Long lectureId, @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(bookmarkService.cancelBookmark(lectureId, userId));
    }

}
