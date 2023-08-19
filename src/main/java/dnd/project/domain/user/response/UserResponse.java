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
        private String accessToken;
        private String refreshToken;

        public static Login response(Users user, String atk, String rtk) {
            return Login.builder()
                    .id(user.getId())
                    .imageUrl(user.getImageUrl())
                    .email(user.getEmail())
                    .name(user.getNickName())
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
        private String nickName;
        private String imageUrl;
        private String interests;

        public static UserResponse.Detail response(Users user) {
            Optional<String> interestOptional = Optional.ofNullable(user.getInterests());
            return Detail.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .nickName(user.getNickName())
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
