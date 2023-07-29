package dnd.project.domain.version.domain.user.service;

import dnd.project.domain.version.domain.user.entity.Authority;
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

import static dnd.project.global.common.Result.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserResponse.Login createUserAccount(UserServiceRequest.CreateUser request) {

        Users user = userRepository.save(
                Users.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .name(request.getName())
                        .authority(Authority.ROLE_USER)
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
    // method

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
