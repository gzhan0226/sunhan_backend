package com.example.sunhan.domain.controller;

import com.example.sunhan.domain.domain.Payment;
import com.example.sunhan.domain.dto.payment.request.InviteStoreRequestDto;
import com.example.sunhan.domain.dto.payment.request.InviteUserRequestDto;
import com.example.sunhan.domain.dto.payment.response.FindStoreInvitationResponseDto;
import com.example.sunhan.domain.dto.payment.response.FindUserInvitationResponseDto;
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

        paymentService.createStoreInvitation(userId,quantity);
        return ResponseEntity.ok("초대를 전송했습니다");
    }

    @PostMapping("/invite/user")
    public ResponseEntity<String> inviteUser(@RequestBody InviteUserRequestDto inviteUserRequestDto) {
        Long storeId = inviteUserRequestDto.storeId();
        int quantity = inviteUserRequestDto.quantity();

        paymentService.createUserInvitation(storeId,quantity);
        return ResponseEntity.ok("초대를 전송했습니다");
    }
    @GetMapping("/invite/store/{uuid}")
    public ResponseEntity<FindStoreInvitationResponseDto> findStoreInvitation(@PathVariable("uuid") String uuid) {
        Payment payment = paymentService.findStoreInvitation(uuid);
        FindStoreInvitationResponseDto findStoreInvitationResponseDto = new FindStoreInvitationResponseDto(
                payment.getId(),payment.getUser(), payment.getQuantity());
        return ResponseEntity.ok(findStoreInvitationResponseDto);
    }

    @GetMapping("/invite/user/{uuid}")
    public ResponseEntity<FindUserInvitationResponseDto> findUserInvitation(@PathVariable("uuid") String uuid) {
        Payment payment = paymentService.findUserInvitation(uuid);
        FindUserInvitationResponseDto findUserInvitationResponseDto = new FindUserInvitationResponseDto(
                payment.getId(), payment.getStore(), payment.getQuantity());
        return ResponseEntity.ok(findUserInvitationResponseDto);
    }
}
