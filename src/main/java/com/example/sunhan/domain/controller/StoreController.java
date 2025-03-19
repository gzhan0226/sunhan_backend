package com.example.sunhan.domain.controller;

import com.example.sunhan.domain.dto.store.request.CreateStoreRequestDto;
import com.example.sunhan.domain.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
@Tag(name = "Store", description = "가게 관련 API")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    @Operation(summary = "가게 정보 추가", description = "가게 정보를 생성")
    public ResponseEntity<String> createStore(@RequestBody CreateStoreRequestDto createStoreRequestDto) {
        Long userId = createStoreRequestDto.userId();
        String name = createStoreRequestDto.name();
        String phoneNumber = createStoreRequestDto.phoneNumber();
        String address = createStoreRequestDto.address();
        storeService.createStore(userId, name, phoneNumber, address);
        return ResponseEntity.ok("가게 정보를 추가하였습니다");
    }
}
