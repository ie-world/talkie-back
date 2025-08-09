// com/example/talkie/jwt/JwtAuthenticationFilter.java
package com.example.talkie.jwt;

import com.example.talkie.repository.RevokedTokenRepository;
import com.example.talkie.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RevokedTokenRepository revokedRepo;

    private static final List<String> PUBLICS = List.of(
            "/api/auth/signup",
            "/api/auth/login",
            "/api/auth/check-username",
            "/api/auth/check-username/**"
    );
    private final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String uri = req.getRequestURI();
        if (isPublic(uri)) {
            chain.doFilter(req, res);
            return;
        }

        String auth = req.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        try {
            String jti = jwtUtil.getJti(auth);
            if (revokedRepo.existsByJti(jti)) {
                res.setStatus(HttpStatus.UNAUTHORIZED.value()); // blacklisted
                return;
            }

            String username = jwtUtil.getUsername(auth);
            var authentication = new UsernamePasswordAuthenticationToken(
                    username, null, List.of() // ROLE 사용시 채우기
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        } catch (Exception e) {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    private boolean isPublic(String uri) {
        return PUBLICS.stream().anyMatch(p -> matcher.match(p, uri));
    }
}
