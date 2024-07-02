package com.tawasalna.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

@Component
@Slf4j
@SuppressWarnings({"deprecation"})
public class JwtUtils implements Serializable {

    @Serial
    private static final long serialVersionUID = 234234523523L;

    @Value("${tawasalna.app.jwtSecret}")
    private String jwtSecret;

    @Value("${tawasalna.app.refreshSecret}")
    private String refreshSecret;

    // login token expiration : 7D
    @Value("${tawasalna.app.jwtExpirationMs}")
    private long jwtExpiration;

    @Value("${tawasalna.app.refreshExpiration}")
    private long refreshExpiration;

    public String parseJwt(HttpServletRequest request) {
        final String headerAuth = request.getHeader("Authorization");

        return headerAuth != null && headerAuth.startsWith("Bearer ") ? headerAuth.substring(7) : null;
    }

    // ------ ACCESS ------

    //validate access
    public Boolean validateAccess(String access, String email) {
        if (access == null) return false;
        return !access.isEmpty() && Objects.equals(getUsernameFromAccess(access), email) && !isAccessExpired(access);
    }

    //generate token for user
    public String generateToken(String sub) {
        return doGenerateToken(sub, jwtExpiration, jwtSecret);
    }

    //retrieve username from jwt token
    public String getUsernameFromAccess(String token) {
        return token == null ? null : getClaimFromToken(token, Claims::getSubject, jwtSecret);
    }

    // ------ REFRESH ------
    public String generateRefreshToken() {
        return doGenerateToken(null, refreshExpiration, refreshSecret);
    }

    public Boolean validateRefresh(String refresh) {
        return refresh != null && !refresh.isEmpty() && !isRefreshExpired(refresh);
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    private String doGenerateToken(String subject, long validity, String secret) {
        JwtBuilder builder = Jwts
                .builder();

        if (subject != null) {
            builder.setSubject(subject);
        }

        return builder
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //check if the token has expired
    private Boolean isAccessExpired(String token) {
        return getExpirationDateFromJwt(token, jwtSecret).before(new Date());
    }

    private Boolean isRefreshExpired(String refresh) {
        return getExpirationDateFromJwt(refresh, refreshSecret).before(new Date());
    }

    private Date getExpirationDateFromJwt(String jwt, String signingKey) {
        return getClaimFromToken(jwt, Claims::getExpiration, signingKey);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, String key) {
        return claimsResolver.apply(getAllClaimsFromToken(token, key));
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token, String key) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
