package com.hospital.apihospital.services.JwtServices;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtTokenService {
    private final UserDetailsService userDetailsService;
    private static final String SECRET_KEY = "secret_key";
    public JwtTokenService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String email){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e){
            System.out.println("Token invalid " + e.getMessage());
            return false;
        }
    }

    public UserDetails getUserDetails(String username){
        return userDetailsService.loadUserByUsername(username);
    }


    public String extractUsername(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


}
