package dnd.project.domain.version.domain.user.controller;

import dnd.project.domain.version.domain.user.request.controller.UserRequest;
import dnd.project.domain.version.domain.user.response.UserResponse;
import dnd.project.domain.version.domain.user.service.UserService;
import dnd.project.global.common.CustomResponseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static dnd.project.domain.version.domain.user.config.Platform.GOOGLE;
import static dnd.project.domain.version.domain.user.config.Platform.KAKAO;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("auth")
    public CustomResponseEntity<UserResponse.Login> createUserAccount(
            @RequestBody @Valid final UserRequest.CreateUser request
    ) {
        return CustomResponseEntity.success(userService.createUserAccount(request.toServiceRequest()));
    }

    // 정보 조회
    @GetMapping("auth/my/info")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CustomResponseEntity<UserResponse.Detail> findMyListUser(
            @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(userService.findMyListUser(userId));
    }

    // 카카오 로그인
    @GetMapping("/login/kakao")
    public CustomResponseEntity<UserResponse.Login> loginByKakao(@RequestParam String code) {
        return CustomResponseEntity.success(userService.loginByOAuth(code, KAKAO));
    }

    // 구글 로그인
    @GetMapping("/login/google")
    public CustomResponseEntity<UserResponse.Login> loginByGoogle(@RequestParam String code) {
        return CustomResponseEntity.success(userService.loginByOAuth(code, GOOGLE));
    }

}
