package com.Management.university.Utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private String secretKey="secretKeysecretKeysecretKey726876826982698297927986";
    public String generateToken(UserDetails userDetails)
    {
        SecretKey keys= Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .issuer(userDetails.getUsername())
                .issuedAt(new Date())
                .claim("username",userDetails.getUsername())
                .claim("role",userDetails.getAuthorities())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*1))
                .signWith(keys)
                .compact();
    }
}
