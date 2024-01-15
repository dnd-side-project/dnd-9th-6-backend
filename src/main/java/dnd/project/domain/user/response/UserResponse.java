package dnd.project.domain.user.response;

import dnd.project.domain.user.entity.Users;
import lombok.*;

import java.util.Optional;


public class UserResponse {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Login {
        private Long id;
        private String imageUrl;
        private String email;
        private String name;
        private String interests;
        private String accessToken;
        private String refreshToken;

        public static Login response(Users user, String atk, String rtk) {

            Optional<String> interestsOptional = Optional.ofNullable(user.getInterests());
            return Login.builder()
                    .id(user.getId())
                    .imageUrl(user.getImageUrl())
                    .email(user.getEmail())
                    .name(user.getNickName())
                    .interests(interestsOptional.orElse(""))
                    .accessToken(atk)
                    .refreshToken(rtk)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Detail {
        private Long id;
        private String email;
        private String name;
        private String imageUrl;
        private String interests;

        public static UserResponse.Detail response(Users user) {
            Optional<String> interestOptional = Optional.ofNullable(user.getInterests());
            return Detail.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getNickName())
                    .imageUrl(user.getImageUrl())
                    .interests(interestOptional.orElse(""))
                    .build();
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    @Builder
    public static class OAuth {
        private String email;
        private String name;
        private Optional<String> profileImageUrl;
    }
}
