package dnd.project.global.common;

import dnd.project.global.config.redis.RedisDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisDao redisDao;

    public Void logoutFromRedis(String email, String accessToken, Long accessTokenExpiration) {
        redisDao.deleteValues(email);
        redisDao.setValues(accessToken, "logout", Duration.ofMillis(accessTokenExpiration));
        return null;
    }

    public Optional<String> getRefreshToken(String email) {
        return Optional.ofNullable(redisDao.getValues(email));
    }

    public void deleteRefreshToken(String email) {
        redisDao.deleteValues(email);
    }
}
