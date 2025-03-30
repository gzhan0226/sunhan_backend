package com.example.sunhan.global.auth.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KakaoLoginPageController {

    @GetMapping("/login/page")
    public String oauth2LoginPage(Model model) {
        // Spring Security가 제공하는 OAuth2 로그인 URL
        String kakaoLoginUrl = "/oauth2/authorization/kakao";
        model.addAttribute("location", kakaoLoginUrl);
        return "login";
    }
}