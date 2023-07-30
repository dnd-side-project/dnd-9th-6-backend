package dnd.project.domain.version.domain.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "google")
public class GoogleProperties {
    private String requestTokenUri;
    private String clientId;
    private String clientSecret;
    private String redirectUri;

    public Map<String, Object> getRequestParameter(String code) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("code", code);
        parameters.put("client_id", clientId);
        parameters.put("client_secret", clientSecret);
        parameters.put("redirect_uri", redirectUri);
        parameters.put("grant_type", "authorization_code");
        return parameters;
    }
}
