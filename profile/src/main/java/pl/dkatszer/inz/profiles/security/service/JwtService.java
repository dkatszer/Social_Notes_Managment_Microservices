package pl.dkatszer.inz.profiles.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.dkatszer.inz.profiles.security.entities.User;
import pl.dkatszer.inz.profiles.security.model.JwtUser;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by doka on 2017-12-16.
 */
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";


    public String generateToken(String username) {
        return generateToken(new HashMap<>(Map.of(
                CLAIM_KEY_USERNAME, username,
                CLAIM_KEY_CREATED, new Date()))
        );
    }

    public Optional<String> getUsernameFromToken(String token) {
        final Optional<Claims> claims = getClaimsFromToken(token);
        if (claims.isPresent()) {
            return Optional.ofNullable(claims.get().getSubject());
        } else {
            return Optional.empty();
        }
    }

    public boolean canTokenBeRefreshed(String token, LocalDateTime lastPasswordReset) {
        final Optional<LocalDateTime> created = getCreatedDateFromToken(token);
        return created.filter(localDateTime ->
                !localDateTime.isBefore(lastPasswordReset) && !isTokenExpired(token)
        ).isPresent();
    }

    public String refreshToken(String token) {
        final Claims claims = getClaimsFromToken(token).get();
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public Boolean validateToken(String token, JwtUser user) {
        return getUsernameFromToken(token).filter(s ->
                s.equals(user.username) && canTokenBeRefreshed(token, user.lastPasswordResetDate)
        ).isPresent();
    }

    private Optional<LocalDateTime> getCreatedDateFromToken(String token) {
        final Optional<Claims> claims = getClaimsFromToken(token);
        if (claims.isPresent()) {
            final Long milliseconds = (Long) claims.get().get(CLAIM_KEY_CREATED);
            return Optional.of(Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime());
        } else {
            return Optional.empty();
        }
    }

    private Boolean isTokenExpired(String token) {
        final Optional<Claims> claims = getClaimsFromToken(token);
        if (claims.isPresent()) {
            final Date expiration = claims.get().getExpiration();
            return expiration.before(new Date());
        } else {
            return true;//todo - is it ok ?
        }
    }

    // IT CANNOT BE IMMUTABLE MAP !!
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Optional<Claims> getClaimsFromToken(String token) {
        try {
            return Optional.of(Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
