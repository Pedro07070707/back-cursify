package com.itb.inf2cm.CursiFy.model.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JwtService {
    private final String secret;

    public JwtService(@Value("${cursify.jwt.secret:change-me-in-dev}") String secret) {
        this.secret = secret;
    }

    public String createAccessToken(Long userId, String email, String role) {
        Instant now = Instant.now();
        Instant expiresAt = now.plus(Duration.ofHours(1));
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("sub", email);
        claims.put("uid", userId);
        claims.put("role", role);
        claims.put("iat", now.getEpochSecond());
        claims.put("exp", expiresAt.getEpochSecond());
        return sign(claims);
    }

    public Long validateAndExtractUserId(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalStateException("Invalid JWT");
        }

        String expectedSignature = base64Url(hmacSha256(parts[0] + "." + parts[1]));
        if (!expectedSignature.equals(parts[2])) {
            throw new IllegalStateException("Invalid JWT signature");
        }

        String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        String expValue = extractJsonValue(payload, "exp");
        if (expValue != null && Instant.now().getEpochSecond() > Long.parseLong(expValue)) {
            throw new IllegalStateException("JWT expired");
        }

        return Long.parseLong(extractJsonValue(payload, "uid"));
    }

    private String sign(Map<String, Object> claims) {
        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payloadJson = toJson(claims);
        String header = base64Url(headerJson.getBytes(StandardCharsets.UTF_8));
        String payload = base64Url(payloadJson.getBytes(StandardCharsets.UTF_8));
        String signature = base64Url(hmacSha256(header + "." + payload));
        return header + "." + payload + "." + signature;
    }

    private byte[] hmacSha256(String input) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to sign JWT", exception);
        }
    }

    private String base64Url(byte[] value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value);
    }

    private String toJson(Map<String, Object> claims) {
        StringBuilder builder = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            if (!first) {
                builder.append(",");
            }
            first = false;
            builder.append("\"").append(entry.getKey()).append("\":");
            Object value = entry.getValue();
            if (value instanceof Number || value instanceof Boolean) {
                builder.append(value);
            } else {
                builder.append("\"").append(String.valueOf(value).replace("\"", "\\\"")).append("\"");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    private String extractJsonValue(String json, String key) {
        String search = "\"" + key + "\":";
        int index = json.indexOf(search);
        if (index < 0) return null;
        int start = index + search.length();
        if (json.charAt(start) == '"') {
            int end = json.indexOf('"', start + 1);
            return json.substring(start + 1, end);
        }
        int end = start;
        while (end < json.length() && "0123456789".indexOf(json.charAt(end)) >= 0) {
            end++;
        }
        return json.substring(start, end);
    }
}
