package com.example.sunhan.domain.controller;

import com.example.sunhan.domain.dto.store.CreateStoreRequestDto;
import com.example.sunhan.domain.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<String> createStore(@RequestBody CreateStoreRequestDto createStoreRequestDto) {
        Long userId = createStoreRequestDto.userId();
        String name = createStoreRequestDto.name();
        String phoneNumber = createStoreRequestDto.phoneNumber();
        String address = createStoreRequestDto.address();
        storeService.createStore(userId, name, phoneNumber, address);
        return ResponseEntity.ok("가게 정보를 추가하였습니다");
    }
}
