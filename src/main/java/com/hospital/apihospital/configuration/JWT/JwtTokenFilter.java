package com.hospital.apihospital.configuration.JWT;

import com.hospital.apihospital.services.JwtServices.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    public JwtTokenFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);

        if (token != null && jwtTokenService.validateToken(token)){
            String username = jwtTokenService.extractUsername(token);
            UserDetails userDetails = jwtTokenService.getUserDetails(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }
}
