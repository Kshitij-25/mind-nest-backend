package com.kshitijcodecraft.mind_nest.service;

import com.kshitijcodecraft.mind_nest.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtils jwtUtils;

    public void blacklistToken(String token) {
        Date expiration = jwtUtils.extractExpiration(token);
        long ttl = expiration.getTime() - System.currentTimeMillis();

        redisTemplate.opsForValue().set(
                token,
                "blacklisted",
                ttl,
                TimeUnit.MILLISECONDS
        );
    }

    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}