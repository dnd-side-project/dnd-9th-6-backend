package dnd.project.domain.user.service;

import dnd.project.domain.user.config.Platform;
import dnd.project.domain.user.response.UserResponse;

public interface OAuth2LoginService {

    Platform supports();
    UserResponse.OAuth toSocialEntityResponse(String code, Platform platform);
}
