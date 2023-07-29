package dnd.project.domain.version.domain.user.request.controller;

import dnd.project.domain.version.domain.user.request.service.UserServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

    @NoArgsConstructor
    @Getter
    public static class CreateUser {

        @NotBlank(message = "이메일은 필수입니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;

        @NotBlank(message = "이름은 필수입니다.")
        private String name;

        public UserServiceRequest.CreateUser toServiceRequest() {
            return UserServiceRequest.CreateUser.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .build();
        }

        // 테스트 생성자
        public CreateUser(String email, String password, String name) {
            this.email = email;
            this.password = password;
            this.name = name;
        }
    }
}
