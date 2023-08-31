package dnd.project.domain.user.request.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

public class UserServiceRequest {

    @AllArgsConstructor(access = PRIVATE)
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Interests {
        @NotNull(message = "interests 는 필수 값 입니다.")
        private List<String> interests;
    }

    @AllArgsConstructor(access = PRIVATE)
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Update {
        private String nickName;
        private List<String> interests;
    }
}
