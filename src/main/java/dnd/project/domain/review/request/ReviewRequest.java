package dnd.project.domain.review.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

public class ReviewRequest {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Create {
        @NotNull(message = "강의 ID는 필수입니다.")
        private Long lectureId;

        @NotNull(message = "강의 점수는 필수입니다.")
        private Double score;

        @NotNull(message = "하나 이상의 태그가 필요합니다.")
        @NotBlank(message = "태그는 빈 문자열을 허용하지 않습니다.")
        private String tags;

        private String content;

        public ReviewServiceRequest.Create toServiceRequest() {
            return ReviewServiceRequest.Create.builder()
                    .lectureId(lectureId)
                    .score(score)
                    .tags(tags)
                    .content(Optional.ofNullable(content))
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Update {
        @NotNull(message = "후기 ID는 필수입니다.")
        private Long reviewId;

        @NotNull(message = "강의 점수는 필수입니다.")
        private Double score;

        @NotNull(message = "하나 이상의 태그가 필요합니다.")
        @NotBlank(message = "태그는 빈 문자열을 허용하지 않습니다.")
        private String tags;

        private String content;

        public ReviewServiceRequest.Update toServiceRequest() {
            return ReviewServiceRequest.Update.builder()
                    .reviewId(reviewId)
                    .score(score)
                    .tags(tags)
                    .content(Optional.ofNullable(content))
                    .build();
        }
    }
}
