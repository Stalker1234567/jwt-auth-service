package com.robo.harvexsolo.controller.filter;

import com.robo.harvexsolo.exception.UserNotFoundException;
import com.robo.harvexsolo.repository.UserRepository;
import com.robo.harvexsolo.service.impl.JwtServiceImpl;
import com.robo.harvexsolo.service.impl.JwtValidatorImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtService;
    private final JwtValidatorImpl jwtValidator;
    private final UserRepository userRepository;
    private final String BEARER_PREFFIX = "Bearer:";


    public UserDetailsService userDetailsService() {
        return username -> userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Auth");
        final String token;
        final String username;

        if(authHeader == null || !authHeader.startsWith(BEARER_PREFFIX)) {
            filterChain.doFilter(request, response);
            return;
        }


        token = authHeader.substring(BEARER_PREFFIX.length());
        username = jwtService.extractUsername(token);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService().loadUserByUsername(username);
            if(jwtValidator.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                log.info("Valid user token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
