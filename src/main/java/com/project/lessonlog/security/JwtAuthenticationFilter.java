package com.project.lessonlog.security;

import com.project.lessonlog.exception.UserNotFoundException;
import com.project.lessonlog.user.domain.User;
import com.project.lessonlog.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@NullMarked
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    private static @Nullable String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = null;
        if (header != null && header.startsWith("Bearer ")) {
            String value = header.substring("Bearer ".length());
            if (StringUtils.hasText(value)) {
                token = value;
            }
        }
        return token;
    }

    private static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(User user) {
        CustomUserDetails userDetails = new CustomUserDetails(user);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null) {
            try {
                Claims claims = jwtProvider.parseToken(token);

                Long userId = Long.valueOf(claims.getSubject());
                User user = userRepository.findById(userId).orElseThrow(
                        () -> new UserNotFoundException(userId));

                UsernamePasswordAuthenticationToken authentication = getUsernamePasswordAuthenticationToken(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException | UserNotFoundException e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
