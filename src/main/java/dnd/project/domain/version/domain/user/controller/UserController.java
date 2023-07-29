package dnd.project.domain.version.domain.user.controller;

import dnd.project.domain.version.domain.user.request.controller.UserRequest;
import dnd.project.domain.version.domain.user.response.UserResponse;
import dnd.project.domain.version.domain.user.service.UserService;
import dnd.project.global.common.CustomResponseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
