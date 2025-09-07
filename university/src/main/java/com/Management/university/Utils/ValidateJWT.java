package com.Management.university.Utils;

import com.Management.university.Service.UniversityUDS;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class ValidateJWT extends OncePerRequestFilter {

    @Autowired
    private UniversityUDS universityUDS;

    private String secretKey="secretKeysecretKeysecretKey726876826982698297927986";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        SecretKey key= Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        String token=request.getHeader("Authorization");
    //    System.out.println(token);
        if(token!=null && token.startsWith("Bearer"))
        {
            String jwt=token.substring(7);
            try
            {
                Claims claims= Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
                String username=claims.get("username").toString();
               // System.out.println(username);
                UserDetails userDetails=universityUDS.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                throw new BadRequestException("Invalid JWT");
            }

        }
        filterChain.doFilter(request,response);

    }
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login");
    }

}
