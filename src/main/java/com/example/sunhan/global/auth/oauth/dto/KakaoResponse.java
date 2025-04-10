package com.example.sunhan.global.auth.oauth.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return (String) properties.get("nickname");
    }

    public String getProfileImage() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return (String) properties.get("profile_image");
    }
}