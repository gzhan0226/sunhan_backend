package com.example.sunhan.domain.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Column(name="password")
    private String password;

    @Column(name="profile_img")
    private String profileImg;

    @Builder
    User(String nickname, String username, String password, String profileImg) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.profileImg = profileImg;
    }
}
