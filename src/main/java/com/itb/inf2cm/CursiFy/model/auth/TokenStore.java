package com.itb.inf2cm.CursiFy.model.auth;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStore {
    private final Map<String, RefreshTokenRecord> refreshTokens = new ConcurrentHashMap<>();
    private final Map<String, RefreshTokenRecord> resetTokens = new ConcurrentHashMap<>();

    public String createRefreshToken(Long userId) {
        String token = UUID.randomUUID().toString();
        refreshTokens.put(token, new RefreshTokenRecord(userId, token, Instant.now().plus(Duration.ofDays(7))));
        return token;
    }

    public Optional<RefreshTokenRecord> validateRefreshToken(String token) {
        RefreshTokenRecord record = refreshTokens.get(token);
        if (record == null || record.getExpiresAt().isBefore(Instant.now())) {
            refreshTokens.remove(token);
            return Optional.empty();
        }
        return Optional.of(record);
    }

    public void revokeRefreshToken(String token) {
        refreshTokens.remove(token);
    }

    public String createResetToken(Long userId) {
        String token = UUID.randomUUID().toString();
        resetTokens.put(token, new RefreshTokenRecord(userId, token, Instant.now().plus(Duration.ofHours(2))));
        return token;
    }

    public Optional<RefreshTokenRecord> validateResetToken(String token) {
        RefreshTokenRecord record = resetTokens.get(token);
        if (record == null || record.getExpiresAt().isBefore(Instant.now())) {
            resetTokens.remove(token);
            return Optional.empty();
        }
        return Optional.of(record);
    }

    public void revokeResetToken(String token) {
        resetTokens.remove(token);
    }
}
