package com.pet.pet_app.security;

import com.pet.pet_app.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtSecurity jwtSecurity;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            final String username = jwtSecurity.extractUsername(jwt);

            if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() ==
                    null) {
                UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

                if (jwtSecurity.isTokenValid(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    SecurityContextHolder.getContext().setAuthentication(
                        authToken);
                }
            }
        } catch (Exception e) {
            logger.warn("JWT processing failed", e);
        }

        filterChain.doFilter(request, response);
    }
}
