package org.project.ebankify_security.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.security.SecurityUser;
import org.project.ebankify_security.security.SecurityUserService;
import org.project.ebankify_security.util.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final SecurityUserService securityUserService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String jwtToken = jwtUtils.getJwtFromHeader(request);
        final String userEmail;

        if (jwtToken == null || !jwtUtils.validateJwtToken(jwtToken, false)) {
            filterChain.doFilter(request, response);
            return;
        }

        userEmail = jwtUtils.getUserNameFromJwtToken(jwtToken, false);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            this.setAuthentication(userEmail, request);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String username, HttpServletRequest request) {
        SecurityUser securityUser = securityUserService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                securityUser,
                null,
                securityUser.getAuthorities()
        );
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}

