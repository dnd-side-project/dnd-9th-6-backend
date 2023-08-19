package dnd.project.domain.user.controller;

import dnd.project.domain.user.config.Platform;
import dnd.project.domain.user.request.controller.UserRequest;
import dnd.project.domain.user.response.UserResponse;
import dnd.project.domain.user.service.UserService;
import dnd.project.global.common.CustomResponseEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    @Value("${jwt.token-validity-in-seconds}")
    long tokenValidityInSeconds;
    @Value("${jwt.refresh-token-validity-in-seconds}")
    long refreshTokenValidityInSeconds;

    private final UserService userService;

    // 소셜 로그인 API
    @GetMapping("/signin")
    public CustomResponseEntity<UserResponse.Login> loginByOAuth(
            @RequestParam String code, @RequestParam Platform platform,
            HttpServletResponse response
    ) {

        UserResponse.Login loginResponse = userService.loginByOAuth(code, platform);
        response.addCookie(createCookie("access_token", loginResponse.getAccessToken(), tokenValidityInSeconds));
        response.addCookie(createCookie("refresh_token", loginResponse.getRefreshToken(), refreshTokenValidityInSeconds));

        return CustomResponseEntity.success(loginResponse);
    }

    // 관심분야 추가 요청 API
    @PostMapping("")
    public CustomResponseEntity<Void> addInterests(
            @AuthenticationPrincipal Long userId, UserRequest.Interests request
    ) {
        return CustomResponseEntity.success(userService.addInterests(userId, request.toServiceRequest()));
    }

    // 내 프로필 조회하기 API
    @GetMapping("")
    public CustomResponseEntity<UserResponse.Detail> detailUser(
            @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(userService.detailUser(userId));
    }

    // 내 정보 수정하기 API
    @PatchMapping("")
    public CustomResponseEntity<UserResponse.Detail> updateUser(
            @RequestBody @Valid UserRequest.Update request, @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(userService.updateUser(request.toServiceRequest(), userId));
    }

    private Cookie createCookie(String name, String value, long expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setMaxAge((int) expiry);

        return cookie;
    }
}
