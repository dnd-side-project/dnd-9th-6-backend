package dnd.project.domain.version.domain.user.service;

import dnd.project.domain.version.domain.user.config.Platform;
import dnd.project.domain.version.domain.user.entity.Users;
import dnd.project.domain.version.domain.user.response.UserResponse;

public interface OAuth2LoginService {

    Platform supports();
    UserResponse.OAuth toSocialEntityResponse(String code, Platform platform);
}
