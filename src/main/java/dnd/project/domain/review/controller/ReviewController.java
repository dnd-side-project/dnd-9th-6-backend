package dnd.project.domain.review.controller;

import dnd.project.domain.review.request.ReviewRequest;
import dnd.project.domain.review.response.ReviewResponse;
import dnd.project.domain.review.service.ReviewService;
import dnd.project.global.common.CustomResponseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    public CustomResponseEntity<ReviewResponse.Create> createReview(
            @RequestBody @Valid ReviewRequest.Create request, @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(reviewService.createReview(request.toServiceRequest(), userId));
    }
}