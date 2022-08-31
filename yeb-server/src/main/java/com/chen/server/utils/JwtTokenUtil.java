package com.chen.server.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 *
 * @author: blkcor
 * @DATE: 2022/3/25  15:34
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@Component
public class JwtTokenUtil {

    public static final String CLAIM_KEY_USERNAME = "sub";
    public static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        //荷载
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 在token中获取用户信息
     *
     * @return
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            username = null;
        }
        return username;
    }

    /**
     * token是否失效
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        return getUsernameFromToken(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 刷新token 生成新的token
     * 本质就是改变过期时间
     *
     * @param token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断是否能刷新token
     *
     * @param token
     * @return
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        return getExpirationFromToken(token).before(new Date());
    }

    /**
     * 根据token拿到过期时间
     *
     * @param token
     * @return
     */
    private Date getExpirationFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * 根据token拿到荷载
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 根据提供的claims生成token
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpiration())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 生成失效时间
     *
     * @return
     */
    private Date generateExpiration() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }


}
