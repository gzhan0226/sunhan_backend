package com.example.sunhan.global.auth.oauth.handler;

import com.example.sunhan.global.auth.jwt.JWTUtil;
import com.example.sunhan.global.auth.oauth.dto.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    public CustomSuccessHandler(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // OAuth2User에서 사용자 정보 추출
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String username = customUserDetails.getName();

        // 권한 정보 추출 (현재는 ROLE_USER만 있다고 가정)
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");

        // JWT 생성
        String token = jwtUtil.createJwt(username, role, 60 * 60 * 60L); // 60분 * 60초 * 60 = 1시간

        System.out.println(username);
        System.out.println(customUserDetails.getNickname());
        System.out.println(customUserDetails.getProfileImg());

        // JWT를 쿼리 파라미터에 담아 프론트로 리다이렉트
        String redirectUrl = "http://localhost:8080/oauth/success?token=" + token;
        response.sendRedirect(redirectUrl);
    }
}