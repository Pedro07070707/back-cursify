package com.itb.inf2cm.CursiFy.model.auth;

import java.time.Instant;

public class RefreshTokenRecord {
    private final Long userId;
    private final String token;
    private final Instant expiresAt;

    public RefreshTokenRecord(Long userId, String token, Instant expiresAt) {
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public Long getUserId() { return userId; }
    public String getToken() { return token; }
    public Instant getExpiresAt() { return expiresAt; }
}
