package dnd.project.domain.user.request.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserServiceRequest {
    @NoArgsConstructor
    @Getter
    public static class CreateUser {
        private String email;
        private String password;
        private String name;

        @Builder
        private CreateUser(String email, String password, String name) {
            this.email = email;
            this.password = password;
            this.name = name;
        }
    }
}
