package dnd.project.domain.review.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

public class ReviewServiceRequest {

    @AllArgsConstructor(access = PRIVATE)
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Create {
        private Long lectureId;
        private Double score;
        private List<String> tags;
        private Optional<String> content;
    }

    @AllArgsConstructor(access = PRIVATE)
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Update {
        @NotNull(message = "후기 ID는 필수입니다.")
        private Long reviewId;

        @NotNull(message = "강의 점수는 필수입니다.")
        private Double score;

        @NotNull(message = "하나 이상의 태그가 필요합니다.")
        @NotBlank(message = "태그는 빈 문자열을 허용하지 않습니다.")
        private String tags;

        private Optional<String> content;
    }

    @AllArgsConstructor(access = PRIVATE)
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Keyword {
        private String keyword;
    }
}
