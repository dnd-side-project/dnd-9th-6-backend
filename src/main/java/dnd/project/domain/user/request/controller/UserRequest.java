package dnd.project.domain.user.request.controller;

import dnd.project.domain.user.request.service.UserServiceRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserRequest {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Interests {
        private List<String> interests;

        public UserServiceRequest.Interests toServiceRequest() {
            return UserServiceRequest.Interests.builder()
                    .interests(interests)
                    .build();
        }
    }
}
