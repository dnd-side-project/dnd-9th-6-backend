package dnd.project.domain.version.domain.user.service;

import dnd.project.domain.version.domain.user.config.Platform;
import dnd.project.domain.version.domain.user.entity.Users;
import dnd.project.domain.version.domain.user.repository.UserRepository;
import dnd.project.domain.version.domain.user.request.service.UserServiceRequest;
import dnd.project.domain.version.domain.user.response.UserResponse;
import dnd.project.global.common.exception.CustomException;
import dnd.project.global.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dnd.project.domain.version.domain.user.entity.Authority.*;
import static dnd.project.global.common.Result.FAIL;
import static dnd.project.global.common.Result.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final List<OAuth2LoginService> oAuth2LoginServices;

    // 일반 회원가입
    @Transactional
    public UserResponse.Login createUserAccount(UserServiceRequest.CreateUser request) {
        Users user = userRepository.save(
                Users.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .name(request.getName())
                        .authority(ROLE_USER)
                        .build()
        );

        // 토큰 발급
        String accessToken = tokenProvider.createToken(
                user.getId(), getAuthentication(request.getEmail(), request.getPassword())
        );
        String refreshToken = tokenProvider.createRefreshToken(request.getEmail());

        return UserResponse.Login.response(user, accessToken, refreshToken);

    }

    public UserResponse.Detail findMyListUser(Long userId) {
        return UserResponse.Detail.response(getUser(userId));
    }

    // 소셜 로그인
    @Transactional
    public UserResponse.Login loginByOAuth(String code, Platform platform) {
        // OAuth 로그인 진행
        UserResponse.OAuth socialLoginUser = toSocialLogin(code, platform);
        Users userEntity = Users.builder()
                .email(socialLoginUser.getEmail())
                .name(socialLoginUser.getName())
                .password(passwordEncoder.encode(platform.name()))
                .authority(ROLE_USER)
                .build();


        // 서비스 회원이 아니면 가입
        Users user = userRepository.findByEmail(userEntity.getEmail())
                .orElseGet(() -> userRepository.save(userEntity));

        // 토큰 발급
        String accessToken = tokenProvider.createToken(
                user.getId(), getAuthentication(user.getEmail(), platform.name())
        );
        String refreshToken = tokenProvider.createRefreshToken(user.getEmail());

        return UserResponse.Login.response(user, accessToken, refreshToken);
    }

    // method

    private UserResponse.OAuth toSocialLogin(String code, Platform platform) {
        UserResponse.OAuth socialLoginUser = null;

        // 인터페이스 구현체를 돌면서 해당되는 플랫폼으로 로그인
        for (OAuth2LoginService oAuth2LoginService : oAuth2LoginServices) {
            if (oAuth2LoginService.supports().equals(platform)) {
                socialLoginUser = oAuth2LoginService.toSocialEntityResponse(code, platform);
                break;
            }
        }

        if (socialLoginUser == null) {
            throw new CustomException(FAIL);
        }

        return socialLoginUser;
    }

    private Authentication getAuthentication(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public Users getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
    }
}
