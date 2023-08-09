package dnd.project.domain.review.request;


import jakarta.validation.constraints.NotNull;
import lombok.*;

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
        private String tags;
        private Optional<String> content;
    }
}
