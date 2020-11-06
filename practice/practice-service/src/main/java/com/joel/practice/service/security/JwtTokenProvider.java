package com.joel.practice.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {
    public static final String TOKEN_TYPE = "Bearer";
    public static final int ACCESS_TOKEN_EXPIRATION_SECONDS = 24 * 3600;
    public static final int REFRESH_TOKEN_EXPIRATION_SECONDS = 60 * 24 * 3600;

    private String secretKey;

    public JwtTokenProvider(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * 解密token，返回主题
     * @param token
     * @return
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .setAllowedClockSkewSeconds(10)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 验证token是否过期
     * @param token
     * @return
     */
    public boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return true;
        }

        Date expiredDate = claims.getExpiration();
        return expiredDate.before(new Date());
    }

    /**
     * 生成拥有过期时间的token
     * @param claims
     * @param expiredDate
     * @return
     */
    public String generateToken(Map<String, Object> claims, Date expiredDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiredDate)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey)),SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 生成没有过期时间的token
     * @param claims
     * @param expiredDate
     * @return
     */
    public String generateToken(Claims claims, Date expiredDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiredDate)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey)),SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     *根据传入的主题和过期时间，和时间调用生成token方法
     * @param sub
     * @param expirationSeconds
     * @return
     */
    public String generateAccessToken(String sub, int expirationSeconds) {
        Date expiredDate = new Date(Instant.now().toEpochMilli() + expirationSeconds * 1000);
        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.SUBJECT, sub);
        return generateToken(claims, expiredDate);
    }

    /**
     *生成token的方法，使用常量作为过期时间
     * @param sub
     * @return
     */
    public String generateAccessToken(String sub) {
        return generateAccessToken(sub, ACCESS_TOKEN_EXPIRATION_SECONDS);
    }

    /**
     * 生成刷新的token
     * @param sub
     * @return
     */
    public String generateRefreshToken(String sub) {
        Date expiredDate = new Date(Instant.now().toEpochMilli() + REFRESH_TOKEN_EXPIRATION_SECONDS * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.SUBJECT, sub);
        return generateToken(claims, expiredDate);
    }

    /**
     * 刷新token，先解密token，并验证，都通过就使用claims的主题重新生成token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }

        if (!validateToken(token)) {
            return null;
        }

        return generateAccessToken(claims.getSubject());
    }
}
