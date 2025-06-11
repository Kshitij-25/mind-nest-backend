package com.kshitijcodecraft.mind_nest.service;

import com.kshitijcodecraft.mind_nest.entity.BlacklistedToken;
import com.kshitijcodecraft.mind_nest.repository.BlacklistedTokenRepository;
import com.kshitijcodecraft.mind_nest.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final BlacklistedTokenRepository repository;

    public void blacklistToken(String token, Date expiry) {
        BlacklistedToken entity = new BlacklistedToken();
        entity.setToken(token);
        entity.setExpiry(expiry.toInstant());
        repository.save(entity);
    }

    public boolean isTokenBlacklisted(String token) {
        return repository.existsByToken(token);
    }

    // Scheduled cleanup (optional)
    @Scheduled(cron = "0 0 * * * *") // every hour
    public void cleanUpExpiredTokens() {
        repository.deleteExpiredTokens(Instant.now());
    }
}
