package com.example.sunhan.global.auth.oauth.handler;

import org.springframework.http.*;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class CustomKakaoTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private final RestTemplate restTemplate;

    public CustomKakaoTokenResponseClient() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest request) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", request.getClientRegistration().getClientId());
        body.add("redirect_uri", request.getAuthorizationExchange().getAuthorizationRequest().getRedirectUri());
        body.add("code", request.getAuthorizationExchange().getAuthorizationResponse().getCode());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                request.getClientRegistration().getProviderDetails().getTokenUri(),
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map<String, Object> responseBody = response.getBody();

        return OAuth2AccessTokenResponse.withToken((String) responseBody.get("access_token"))
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .expiresIn(((Number) responseBody.get("expires_in")).longValue())
                .refreshToken((String) responseBody.get("refresh_token"))
                .scopes(request.getClientRegistration().getScopes())
                .build();
    }
}