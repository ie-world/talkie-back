// com/example/talkie/util/JwtUtil.java
package com.example.talkie.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;               // >= 32 bytes

    @Value("${jwt.exp-min:60}")
    private long expMin;

    public String createToken(String username) {
        Instant now = Instant.now();
        Instant exp = now.plus(expMin, ChronoUnit.MINUTES);

        return Jwts.builder()
                .setSubject(username)
                .setId(UUID.randomUUID().toString())           // jti
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String bearerOrToken) {
        return parse(bearerOrToken).getBody().getSubject();
    }

    public String getJti(String bearerOrToken) {
        return parse(bearerOrToken).getBody().getId();
    }

    public Instant getExpiry(String bearerOrToken) {
        return parse(bearerOrToken).getBody().getExpiration().toInstant();
    }

    private Jws<Claims> parse(String bearerOrToken) {
        String token = stripBearer(bearerOrToken);
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token);
    }

    private Key key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private String stripBearer(String token) {
        return token != null && token.startsWith("Bearer ") ? token.substring(7) : token;
    }
}
