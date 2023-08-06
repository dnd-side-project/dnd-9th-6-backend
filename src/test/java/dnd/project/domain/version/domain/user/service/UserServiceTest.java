package dnd.project.domain.version.domain.user.service;

import dnd.project.domain.user.request.controller.UserRequest;
import dnd.project.domain.user.response.UserResponse;
import dnd.project.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @DisplayName("유저가 회원가입을 한다.")
    @Test
    void createAccount() {
        // given
        UserRequest.CreateUser request = new UserRequest.CreateUser("test@test.com", "abc123", "테스트");

        // when
        UserResponse.Login response = userService.createUserAccount(request.toServiceRequest());

        // then
        assertThat(response.getEmail()).isEqualTo("test@test.com");
        assertThat(response.getAccessToken()).isNotNull();
        assertThat(response.getRefreshToken()).isNotNull();
    }
}