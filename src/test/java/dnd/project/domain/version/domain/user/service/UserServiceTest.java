package dnd.project.domain.version.domain.user.service;

import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import dnd.project.domain.user.request.controller.UserRequest;
import dnd.project.domain.user.request.service.UserServiceRequest;
import dnd.project.domain.user.service.UserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dnd.project.domain.user.entity.Authority.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


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

    @DisplayName("유저가 첫 로그인 후 관심분야를 설정한다.")
    @Test
    void addInterests() {
        // given
        Users user = userRepository.save(
                Users.builder()
                        .email("test@test.com")
                        .password("test")
                        .nickName("test")
                        .authority(ROLE_USER)
                        .build()
        );

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
        Users user = userRepository.save(
                Users.builder()
                        .email("test@test.com")
                        .password("test")
                        .nickName("test")
                        .authority(ROLE_USER)
                        .build()
        );

        UserServiceRequest.Interests request = UserServiceRequest.Interests.builder()
                .interests(List.of())
                .build();

        // when // then
        assertThatThrownBy(() -> userService.addInterests(user.getId(), request))
                .extracting("result.code", "result.message")
                .contains(-1001, "하나 이상의 관심분야를 선택해야 합니다.");
    }
}