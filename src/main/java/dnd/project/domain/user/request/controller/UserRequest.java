package dnd.project.domain.user.request.controller;

import dnd.project.domain.user.request.service.UserServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserRequest {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Interests {
        @NotNull
        private List<String> interests;

        public UserServiceRequest.Interests toServiceRequest() {
            return UserServiceRequest.Interests.builder()
                    .interests(interests)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Update {
        @NotNull
        @NotBlank
        private String nickName;
        @NotNull
        private List<String> interests;

        public UserServiceRequest.Update toServiceRequest() {
            return UserServiceRequest.Update.builder()
                    .nickName(nickName)
                    .interests(interests)
                    .build();
        }
    }
}
