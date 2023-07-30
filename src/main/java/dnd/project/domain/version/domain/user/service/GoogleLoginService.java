package dnd.project.domain.version.domain.user.service;

import dnd.project.domain.version.domain.user.config.GoogleProperties;
import dnd.project.domain.version.domain.user.config.Platform;
import dnd.project.domain.version.domain.user.entity.Authority;
import dnd.project.domain.version.domain.user.entity.Users;
import dnd.project.domain.version.domain.user.response.GoogleTokenResponse;
import dnd.project.domain.version.domain.user.response.GoogleUserResponse;
import dnd.project.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static dnd.project.domain.version.domain.user.entity.Authority.ROLE_USER;
import static dnd.project.global.common.Result.FAIL;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {

    private final PasswordEncoder passwordEncoder;
    private final GoogleProperties googleProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    // 유저 Entity 로 변환
    public Users toEntityUser(String code, Platform platform) {
        String accessToken = toRequestAccessToken(code);
        GoogleUserResponse profile = toRequestProfile(accessToken);

        return Users.builder()
                .email(profile.getEmail())
                .name(profile.getName())
                .password(passwordEncoder.encode(platform.name()))
                .authority(ROLE_USER)
                .build();
    }

    // Google AccessToken 응답
    private String toRequestAccessToken(String code) {
        // 발급받은 code -> GET 요청
        ResponseEntity<GoogleTokenResponse> response = restTemplate.postForEntity(
                googleProperties.getRequestTokenUri(),
                googleProperties.getRequestParameter(code),
                GoogleTokenResponse.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException(FAIL);
        }

        return response.getBody().getAccessToken();
    }

    // 유저 정보 응답
    private GoogleUserResponse toRequestProfile(String accessToken) {
        // accessToken 헤더 등록
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        // GET 요청으로 유저정보 응답 시도
        ResponseEntity<GoogleUserResponse> response =
                restTemplate.exchange("https://www.googleapis.com/oauth2/v1/userinfo", HttpMethod.GET, request, GoogleUserResponse.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException(FAIL);
        }

        return response.getBody();
    }
}
