package dev.felipeoj.users_crud.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final StringRedisTemplate redisTemplate;

    public void saveRefreshToken(String userId, String refreshToken, Duration ttl) {
        redisTemplate.opsForValue().set("refresh:" + userId, refreshToken, ttl);
    }

    public String getRefreshToken(String userId) {
        return redisTemplate.opsForValue().get("refresh:" + userId);
    }

    public void removeRefreshToken(String userId) {
        redisTemplate.delete("refresh:" + userId);
    }

    public boolean refreshTokenExists(String userId, String refreshToken) {
        String token = redisTemplate.opsForValue().get("refresh:" + userId);
        if (token == null) {
            return false;
        }
        if(!redisTemplate.hasKey("refresh:" + userId)) {
            return false;
        }
        if(isBlacklisted(refreshToken)){
            return false;
        }
        return refreshToken.equals(token);
    }

    public void addToBlacklist(String token) {
        redisTemplate.opsForValue().set("blacklist:" + token, "invalid", Duration.ofMinutes(30));
    }

    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:" + token));
    }
}
