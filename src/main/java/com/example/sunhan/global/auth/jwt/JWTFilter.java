package com.example.sunhan.global.auth.jwt;

import com.example.sunhan.domain.domain.User;
import com.example.sunhan.domain.repository.UserRepository;
import com.example.sunhan.global.auth.oauth.dto.CustomOAuth2User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Authorization 헤더에서 토큰 추출
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // "Bearer " 제거

        // 2. 토큰 만료 확인
        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 토큰에서 username 추출
        String username = jwtUtil.getUsername(token);

        // 4. DB에서 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 5. CustomOAuth2User 생성 (attributes는 null 또는 Map.of()로 처리)
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(user, Map.of());

        // 6. 인증 객체 생성 및 SecurityContext에 등록
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                customOAuth2User,
                null,
                customOAuth2User.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}