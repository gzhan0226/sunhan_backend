package com.example.sunhan.domain.controller;

import com.example.sunhan.domain.dto.store.CreateStoreRequestDto;
import com.example.sunhan.domain.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<String> createStore(@ModelAttribute CreateStoreRequestDto createStoreRequestDto) {
        storeService.createStore(createStoreRequestDto);
        return ResponseEntity.ok("가게 정보를 추가하였습니다");
    }
}
