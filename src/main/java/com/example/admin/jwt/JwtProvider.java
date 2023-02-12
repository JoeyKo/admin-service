package com.example.admin.jwt;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import com.example.admin.controller.UserController;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtProvider {

    private static final Key SECRET_KEY = new SecretKeySpec("F@cturacionSR12023F@cturacionSR12023F@cturacionSR12023".getBytes(), SignatureAlgorithm.HS512.getJcaName());

    private static final long TIME_EXPIRATION = 3600_000;

    public static String generateTokenJWT(String role) {
        return Jwts.builder().setSubject(role)
                .setExpiration(Date.from(Instant.now().plusMillis(TIME_EXPIRATION))).signWith(SECRET_KEY)
                .compact();
    }

    public static boolean validateTokenJWT(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUsername(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();
        return parser.parseClaimsJws(token).getBody().getSubject();
    }

}
