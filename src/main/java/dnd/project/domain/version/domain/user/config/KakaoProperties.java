package dnd.project.domain.version.domain.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kakao")
public class KakaoProperties {

    private String tokenUri;
    private String userInfoUri;
    private String grantType;
    private String clientId;
    private String redirectUri;
}
