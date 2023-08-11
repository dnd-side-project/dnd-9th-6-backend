package dnd.project.domain.user.service;

import dnd.project.domain.user.config.Platform;
import dnd.project.domain.user.entity.Authority;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import dnd.project.domain.user.request.controller.UserRequest;
import dnd.project.domain.user.request.service.UserServiceRequest;
import dnd.project.domain.user.response.UserResponse;
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
import java.util.Optional;

import static dnd.project.global.common.Result.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final List<OAuth2LoginService> oAuth2LoginServices;

    // 내 프로필 조회하기 API
    public UserResponse.Detail detailUser(Long userId) {
        return UserResponse.Detail.response(getUser(userId));
    }

    // 소셜 로그인 API
    @Transactional
    public UserResponse.Login loginByOAuth(String code, Platform platform) {
        // OAuth 로그인 진행
        boolean isRegister = false;
        UserResponse.OAuth socialLoginUser = toSocialLogin(code, platform);
        Users userEntity = Users.builder()
                .email(socialLoginUser.getEmail())
                .nickName(socialLoginUser.getName())
                .password(passwordEncoder.encode(platform.name()))
                .authority(Authority.ROLE_USER)
                .build();


        // 서비스 회원이 아니면 가입
        Users user;
        Optional<Users> optionalUser = userRepository.findByEmail(userEntity.getEmail());
        if (optionalUser.isEmpty()) {
            user = userRepository.save(userEntity);
            isRegister = true;
        } else {
            user = optionalUser.get();
        }

        // 토큰 발급
        String accessToken = tokenProvider.createToken(
                user.getId(), getAuthentication(user.getEmail(), platform.name())
        );
        String refreshToken = tokenProvider.createRefreshToken(user.getEmail());

        return UserResponse.Login.response(user, accessToken, refreshToken, isRegister);
    }

    // 관심분야 요청 API
    @Transactional
    public Void addInterests(Long userId, UserServiceRequest.Interests request) {
        List<String> interests = request.getInterests();

        if (interests.isEmpty()) {
            throw new CustomException(AT_LEAST_ONE_INTEREST_REQUIRED);
        }

        Users user = getUser(userId);
        user.toUpdateInterests(String.join(",", interests));
        return null;
    }

    // 내 정보 수정 API
    @Transactional
    public UserResponse.Detail updateUser(UserServiceRequest.Update request, Long userId) {
        Users user = getUser(userId);
        List<String> interests = request.getInterests();

        if (interests.isEmpty()) {
            throw new CustomException(AT_LEAST_ONE_INTEREST_REQUIRED);
        }

        user.toUpdateProfile(request.getNickName(), String.join(",", interests));
        return null;
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
