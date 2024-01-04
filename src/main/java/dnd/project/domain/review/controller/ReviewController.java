package dnd.project.domain.review.controller;

import dnd.project.domain.review.request.ReviewRequest;
import dnd.project.domain.review.service.ReviewService;
import dnd.project.global.common.CustomResponseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static dnd.project.domain.review.response.ReviewResponse.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 후기 작성 API
    @PostMapping("/review")
    public CustomResponseEntity<Create> createReview(
            @RequestBody @Valid ReviewRequest.Create request, @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(reviewService.createReview(request.toServiceRequest(), userId));
    }

    // 후기 삭제 API
    @DeleteMapping("/review")
    public CustomResponseEntity<Void> deleteReview(
            @RequestParam Long reviewId, @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(reviewService.deleteReview(reviewId, userId));
    }

    // 후기 수정 API
    @PatchMapping("/review")
    public CustomResponseEntity<Create> updateReview(
            @RequestBody @Valid ReviewRequest.Update request, @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(reviewService.updateReview(request.toServiceRequest(), userId));
    }

    // 후기 좋아요 및 취소 API
    @PostMapping("/review/like")
    public CustomResponseEntity<ToggleLike> toggleLikeReview(
            @RequestParam Long reviewId, @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(reviewService.toggleLikeReview(reviewId, userId));
    }

    // 최근 올라온 후기 조회 API
    @GetMapping("/review/recent")
    public CustomResponseEntity<List<ReadDetails>> readRecentReview(
            @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(reviewService.readRecentReview(userId));
    }

    // 내 후기 조회 API
    @GetMapping("/review")
    public CustomResponseEntity<List<ReadMyDetails>> readMyReviews(
            @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(reviewService.readMyReviews(userId));
    }
}
