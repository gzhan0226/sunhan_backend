package com.example.sunhan.domain.controller;

import com.example.sunhan.domain.domain.Payment;
import com.example.sunhan.domain.dto.payment.request.AcceptStoreInvitationRequestDto;
import com.example.sunhan.domain.dto.payment.request.AcceptUserInvitationRequestDto;
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

        return ResponseEntity.ok(paymentService.createStoreInvitation(userId,quantity));
    }

    @PostMapping("/invite/user")
    public ResponseEntity<String> inviteUser(@RequestBody InviteUserRequestDto inviteUserRequestDto) {
        Long storeId = inviteUserRequestDto.storeId();
        int quantity = inviteUserRequestDto.quantity();

        return ResponseEntity.ok(paymentService.createUserInvitation(storeId,quantity));
    }
    @GetMapping("/invite/store/{uuid}")
    public ResponseEntity<FindStoreInvitationResponseDto> findStoreInvitation(@PathVariable("uuid") String uuid) {
        Payment payment = paymentService.findStoreInvitation(uuid);
        FindStoreInvitationResponseDto findStoreInvitationResponseDto = new FindStoreInvitationResponseDto(
                payment.getId(),payment.getUser().getId(),payment.getUser().getUsername(), payment.getUser().getProfileImg(),
                payment.getQuantity());
        return ResponseEntity.ok(findStoreInvitationResponseDto);
    }

    @GetMapping("/invite/user/{uuid}")
    public ResponseEntity<FindUserInvitationResponseDto> findUserInvitation(@PathVariable("uuid") String uuid) {
        Payment payment = paymentService.findUserInvitation(uuid);
        FindUserInvitationResponseDto findUserInvitationResponseDto = new FindUserInvitationResponseDto(
                payment.getId(), payment.getStore().getId(), payment.getStore().getName(), payment.getStore().getPhoneNumber()
                , payment.getStore().getAddress(), payment.getQuantity());
        return ResponseEntity.ok(findUserInvitationResponseDto);
    }

    @PostMapping("/invite/store/{uuid}")
    public ResponseEntity<String> acceptStoreInvitation(@PathVariable("uuid") String uuid,
                                                        @RequestBody AcceptStoreInvitationRequestDto acceptStoreInvitationRequestDto) {
        Long storeId = acceptStoreInvitationRequestDto.storeId();
        paymentService.acceptStoreInvitation(uuid,storeId);
        return ResponseEntity.ok("선결제가 완료되었습니다");
    }

    @PostMapping("/invite/user/{uuid}")
    public ResponseEntity<String> acceptUserInvitation(@PathVariable("uuid") String uuid,
                                                       @RequestBody AcceptUserInvitationRequestDto acceptUserInvitationRequestDto) {
        Long userId = acceptUserInvitationRequestDto.userId();
        paymentService.acceptUserInvitation(uuid,userId);
        return ResponseEntity.ok("선결제가 완료되었습니다");
    }
}
