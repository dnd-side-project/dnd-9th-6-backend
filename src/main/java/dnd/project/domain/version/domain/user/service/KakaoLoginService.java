package dnd.project.domain.version.domain.user.service;

import dnd.project.domain.version.domain.user.config.KakaoProperties;
import dnd.project.domain.version.domain.user.config.Platform;
import dnd.project.domain.version.domain.user.entity.Authority;
import dnd.project.domain.version.domain.user.entity.Users;
import dnd.project.domain.version.domain.user.response.KakaoTokenResponse;
import dnd.project.domain.version.domain.user.response.KakaoUserResponse;
import dnd.project.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static dnd.project.domain.version.domain.user.entity.Authority.*;
import static dnd.project.global.common.Result.*;


@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate = new RestTemplate();
    private final KakaoProperties kakaoProperties;

    // 유저 Entity 로 변환
    public Users toEntityUser(String code, Platform platform) {
        String accessToken = toRequestAccessToken(code);
        KakaoUserResponse profile = toRequestProfile(accessToken);

        return Users.builder()
                .email(profile.getKakaoAccount().getEmail())
                .name(profile.getProperties().getNickname())
                .password(passwordEncoder.encode(platform.name()))
                .authority(ROLE_USER)
                .build();
    }

    // Google AccessToken 응답
    private String toRequestAccessToken(String authorizationCode) {
        // 발급받은 code -> GET 요청
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", kakaoProperties.getGrantType());
        params.add("client_id", kakaoProperties.getClientId());
        params.add("redirect_uri", kakaoProperties.getRedirectUri());
        params.add("code", authorizationCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<KakaoTokenResponse> response =
                restTemplate.postForEntity(kakaoProperties.getTokenUri(), request, KakaoTokenResponse.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException(FAIL);
        }

        return response.getBody().getAccessToken();
    }

    // 유저 정보 응답
    private KakaoUserResponse toRequestProfile(String accessToken) {
        // accessToken 헤더 등록
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        // GET 요청으로 유저정보 응답 시도
        ResponseEntity<KakaoUserResponse> response = restTemplate.postForEntity(
                kakaoProperties.getUserInfoUri(), new HttpEntity<>(headers), KakaoUserResponse.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException(FAIL);
        }

        return response.getBody();
    }

}
