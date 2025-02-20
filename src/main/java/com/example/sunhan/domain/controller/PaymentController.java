package com.example.sunhan.domain.controller;

import com.example.sunhan.domain.dto.payment.request.InviteStoreRequestDto;
import com.example.sunhan.domain.dto.payment.request.InviteUserRequestDto;
import com.example.sunhan.domain.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/invite/store")
    public ResponseEntity<String> inviteStore(@RequestBody InviteStoreRequestDto inviteStoreRequestDto) {
        Long userId = inviteStoreRequestDto.userId();
        int quantity = inviteStoreRequestDto.quantity();

        paymentService.createStoreInvitement(userId,quantity);
        return ResponseEntity.ok("초대를 전송했습니다");
    }

    @PostMapping("/invite/user")
    public ResponseEntity<String> inviteUser(@RequestBody InviteUserRequestDto inviteUserRequestDto) {
        Long storeId = inviteUserRequestDto.storeId();
        int quantity = inviteUserRequestDto.quantity();

        paymentService.createUserInvitement(storeId,quantity);
        return ResponseEntity.ok("초대를 전송했습니다");
    }

}
