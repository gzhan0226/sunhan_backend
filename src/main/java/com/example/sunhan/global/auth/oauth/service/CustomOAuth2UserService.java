package com.example.sunhan.global.auth.oauth.service;

import com.example.sunhan.domain.domain.User;
import com.example.sunhan.domain.repository.UserRepository;
import com.example.sunhan.global.auth.oauth.dto.CustomOAuth2User;
import com.example.sunhan.global.auth.oauth.dto.KakaoResponse;
import com.example.sunhan.global.auth.oauth.dto.OAuth2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

       final OAuth2Response oAuth2Response;

        if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else {
            throw new OAuth2AuthenticationException("Unsupported provider");
        }

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();

        User user = userRepository.findByUsername(username)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .nickname(oAuth2Response.getName())
                            .username(username)
                            .profileImg(oAuth2Response.getProfileImage())
                            .password("NO_PASSWORD") // 소셜 로그인 사용자는 비밀번호가 없을 수 있음
                            .build();
                    return userRepository.save(newUser);
                });

        // 사용자 인증 정보로 반환
        return new CustomOAuth2User(user, oAuth2User.getAttributes());
    }
}
