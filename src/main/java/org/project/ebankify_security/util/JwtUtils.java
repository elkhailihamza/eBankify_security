package org.project.ebankify_security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.project.ebankify_security.security.SecurityUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.application.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.application.jwtRefreshSecret}")
    private String jwtRefreshSecret;

    @Value("${spring.application.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${spring.application.jwtRefreshTokenExpirationMs}")
    private int jwtRefreshTokenExpirationMs;

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String generateRefreshTokenFromUsername(UserDetails userDetails) {
        String username = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtRefreshTokenExpirationMs))
                .signWith(key(true))
                .compact();
    }

    public String generateTokenFromUsername(SecurityUser securityUser) {
        String username = securityUser.getUsername();
        long id = securityUser.getId();
        Collection<? extends GrantedAuthority> roles = securityUser.getAuthorities();

        List<String> roleNames = roles.stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.replace("ROLE_", ""))
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("roles", roleNames);

        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(false))
                .compact();
    }


    public String getUserNameFromJwtToken(String token, boolean isRefreshToken) {
        return Jwts.parser()
                .verifyWith((SecretKey) key(isRefreshToken))
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    private Key key(boolean isRefreshToken) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(isRefreshToken ? jwtRefreshSecret : jwtSecret));
    }

    public boolean validateJwtToken(String authToken, boolean isRefreshToken) throws ExpiredJwtException {
        try {
            Jwts.parser().verifyWith((SecretKey) key(isRefreshToken)).build().parseSignedClaims(authToken);
            return true;
        } catch(ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public boolean checkIfJwtIsExpired(String authToken, boolean isRefreshToken) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith((SecretKey) key(isRefreshToken))
                    .build()
                    .parseSignedClaims(authToken)
                    .getPayload();

            Date expirationDate = claims.getExpiration();

            return expirationDate.before(new Date());
        } catch (Exception e) {
            logger.error("Error checking token expiration: {}", e.getMessage());
            return false;
        }
    }


    public Date getExpirationDate() {
        return new Date((new Date()).getTime() + jwtExpirationMs);
    }
}