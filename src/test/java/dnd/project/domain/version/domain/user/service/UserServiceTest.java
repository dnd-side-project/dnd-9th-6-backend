package dnd.project.domain.version.domain.user.service;

import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import dnd.project.domain.user.request.controller.UserRequest;
import dnd.project.domain.user.request.service.UserServiceRequest;
import dnd.project.domain.user.response.UserResponse;
import dnd.project.domain.user.service.UserService;
import dnd.project.global.common.RedisService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static dnd.project.domain.user.entity.Authority.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @DisplayName("유저가 첫 로그인 후 관심분야를 설정한다.")
    @Test
    void addInterests() {
        // given
        Users user = saveUser();

        UserRequest.Interests request = new UserRequest.Interests(List.of("프로그래밍,금융투자,데이터 사이언스"));

        entityManager.flush();
        entityManager.clear();

        // when
        userService.addInterests(user.getId(), request.toServiceRequest());

        // then
        Users validateUser = userRepository.findById(user.getId()).get();
        assertThat(validateUser)
                .extracting("id", "email", "interests")
                .contains(user.getId(), user.getEmail(), "프로그래밍,금융투자,데이터 사이언스");
    }

    @DisplayName("유저가 첫 로그인 후 관심분야를 하나도 고르지 않아 Exception 발생한다.")
    @Test
    void NotChoiceInterestThrowException() {
        // given
        Users user = saveUser();

        UserServiceRequest.Interests request = UserServiceRequest.Interests.builder()
                .interests(List.of())
                .build();

        // when // then
        assertThatThrownBy(() -> userService.addInterests(user.getId(), request))
                .extracting("result.code", "result.message")
                .contains(-1001, "하나 이상의 관심분야를 선택해야 합니다.");
    }

    @DisplayName("유저가 자신의 프로필 정보를 확인한다.")
    @Test
    void detailUser() {
        // given
        Users user = saveUser();

        // when
        UserResponse.Detail response = userService.detailUser(user.getId());

        // then
        assertThat(response)
                .extracting("id", "email", "nickName", "imageUrl", "interests")
                .contains(
                        user.getId(), user.getEmail(), user.getNickName(),
                        user.getImageUrl(), user.getInterests()
                );
    }

    @DisplayName("유저가 관심분야가 설정되지 않은 상태에서 자신의 프로필 정보를 확인한다.")
    @Test
    void detailUserNotInterest() {
        // given
        Users user = saveUserNotInterest();

        // when
        UserResponse.Detail response = userService.detailUser(user.getId());

        // then
        assertThat(response)
                .extracting("id", "email", "nickName", "imageUrl", "interests")
                .contains(
                        user.getId(), user.getEmail(), user.getNickName(),
                        user.getImageUrl(), ""
                );
    }

    @DisplayName("유저가 자신의 프로필 정보를 수정한다.")
    @Test
    void updateUser() {
        // given
        Users user = saveUser();
        UserRequest.Update request = new UserRequest.Update("클래스코프", List.of("프로그래밍,커리어"));

        entityManager.flush();
        entityManager.clear();
        // when
        userService.updateUser(request.toServiceRequest(), user.getId());

        // then
        Users validateUser = userRepository.findById(user.getId()).get();
        assertThat(validateUser)
                .extracting("nickName", "interests")
                .contains("클래스코프", "프로그래밍,커리어");
    }

    @DisplayName("유저가 회원탈퇴를 진행한다.")
    @Test
    void withdraw() {
        // given
        Users user = saveUser();
        given(redisService.getRefreshToken(any()))
                .willReturn(Optional.of("REFRESH_TOKEN"));

        // when
        userService.withdraw("REFRESH_TOKEN", user.getId());

        // then
        Optional<Users> optionalUser = userRepository.findById(user.getId());
        assertThat(optionalUser.isEmpty()).isTrue();
    }

    // method

    private Users saveUser() {
        return userRepository.save(
                Users.builder()
                        .email("test@test.com")
                        .password("test")
                        .nickName("test")
                        .imageUrl("http://aws.amazon...s3/image.png")
                        .interests("디자인,드로잉")
                        .authority(ROLE_USER)
                        .build()
        );
    }

    private Users saveUserNotInterest() {
        return userRepository.save(
                Users.builder()
                        .email("test@test.com")
                        .password("test")
                        .nickName("test")
                        .imageUrl("http://aws.amazon...s3/image.png")
                        .interests(null)
                        .authority(ROLE_USER)
                        .build()
        );
    }

}