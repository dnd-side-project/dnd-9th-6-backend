package dnd.project.domain.user.request.service;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

import static lombok.AccessLevel.*;

public class UserServiceRequest {

    @AllArgsConstructor(access = PRIVATE)
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Interests {
        @NotNull(message = "interests 는 필수 값 입니다.")
        private List<String> interests;
    }
}
