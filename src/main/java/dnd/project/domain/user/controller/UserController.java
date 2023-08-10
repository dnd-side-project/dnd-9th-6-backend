package dnd.project.domain.user.controller;

import dnd.project.domain.user.request.controller.UserRequest;
import dnd.project.domain.user.response.UserResponse;
import dnd.project.domain.user.service.UserService;
import dnd.project.global.common.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static dnd.project.domain.user.config.Platform.GOOGLE;
import static dnd.project.domain.user.config.Platform.KAKAO;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 카카오 로그인 API
    @GetMapping("/login/kakao")
    public CustomResponseEntity<UserResponse.Login> loginByKakao(@RequestParam String code) {
        return CustomResponseEntity.success(userService.loginByOAuth(code, KAKAO));
    }

    // 구글 로그인 API
    @GetMapping("/login/google")
    public CustomResponseEntity<UserResponse.Login> loginByGoogle(@RequestParam String code) {
        return CustomResponseEntity.success(userService.loginByOAuth(code, GOOGLE));
    }

    // 관심분야 추가 요청 API
    @PostMapping("/auth")
    public CustomResponseEntity<Void> addInterests(
            @AuthenticationPrincipal Long userId, UserRequest.Interests request
    ) {
        return CustomResponseEntity.success(userService.addInterests(userId, request.toServiceRequest()));
    }

    // 내 프로필 조회하기 API
    @GetMapping("/auth")
    public CustomResponseEntity<UserResponse.Detail> detailUser(
            @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(userService.detailUser(userId));
    }

}
