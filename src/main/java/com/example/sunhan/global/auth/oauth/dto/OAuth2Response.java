package com.example.sunhan.global.auth.oauth.dto;

public interface OAuth2Response {
    String getProvider();      // ex: kakao
    String getProviderId();    // ex: 카카오 user ID
    String getName();          // 닉네임

    default String getProfileImage() {
        return null;
    }
}
