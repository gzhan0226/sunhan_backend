package com.example.sunhan.domain.controller;

import com.example.sunhan.domain.domain.Payment;
import com.example.sunhan.domain.dto.payment.request.AcceptStoreInvitationRequestDto;
import com.example.sunhan.domain.dto.payment.request.InviteStoreRequestDto;
import com.example.sunhan.domain.dto.payment.request.InviteUserRequestDto;
import com.example.sunhan.domain.dto.payment.response.FindMyPaymentResponseDto;
import com.example.sunhan.domain.dto.payment.response.FindStoreInvitationResponseDto;
import com.example.sunhan.domain.dto.payment.response.FindUserInvitationResponseDto;
import com.example.sunhan.domain.service.PaymentService;
import com.example.sunhan.global.auth.oauth.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
@Tag(name = "Payment", description = "선결제 관련 API")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/invite/store")
    @Operation(summary = "유저 -> 가게 선결제 요청", description = "유저가 가게에 선결제 요청을 보냅니다")
    public ResponseEntity<String> inviteStore(@RequestBody InviteStoreRequestDto inviteStoreRequestDto,
                                              @AuthenticationPrincipal CustomOAuth2User user) {
        Long userId = user.getUserId();
        int quantity = inviteStoreRequestDto.quantity();

        return ResponseEntity.ok(paymentService.createStoreInvitation(userId,quantity));
    }

    @PostMapping("/invite/user")
    @Operation(summary = "가게 -> 유저 선결제 요청", description = "가게가 유저에 선결제 요청을 보냅니다")
    public ResponseEntity<String> inviteUser(@RequestBody InviteUserRequestDto inviteUserRequestDto) {
        Long storeId = inviteUserRequestDto.storeId();
        int quantity = inviteUserRequestDto.quantity();

        return ResponseEntity.ok(paymentService.createUserInvitation(storeId,quantity));
    }
    @GetMapping("/invite/store/{uuid}")
    @Operation(summary = "유저 -> 가게 선결제 요청 조회", description = "유저가 가게에 보낸 선결제 요청을 조회합니다")
    public ResponseEntity<FindStoreInvitationResponseDto> findStoreInvitation(@PathVariable("uuid") String uuid) {
        Payment payment = paymentService.findStoreInvitation(uuid);
        FindStoreInvitationResponseDto findStoreInvitationResponseDto = new FindStoreInvitationResponseDto(
                payment.getId(),payment.getUser().getId(),payment.getUser().getNickname(), payment.getUser().getProfileImg(),
                payment.getQuantity());
        return ResponseEntity.ok(findStoreInvitationResponseDto);
    }

    @GetMapping("/invite/user/{uuid}")
    @Operation(summary = "가게 -> 유저 선결제 요청 조회", description = "가게가 유저에 보낸 선결제 요청을 조회합니다")
    public ResponseEntity<FindUserInvitationResponseDto> findUserInvitation(@PathVariable("uuid") String uuid) {
        Payment payment = paymentService.findUserInvitation(uuid);
        FindUserInvitationResponseDto findUserInvitationResponseDto = new FindUserInvitationResponseDto(
                payment.getId(), payment.getStore().getId(), payment.getStore().getName(), payment.getStore().getPhoneNumber()
                , payment.getStore().getAddress(), payment.getQuantity());
        return ResponseEntity.ok(findUserInvitationResponseDto);
    }

    @PostMapping("/invite/store/{uuid}")
    @Operation(summary = "유저 -> 가게 선결제 승인", description = "유저가 가게에 보낸 선결제 요청을 승인합니다")
    public ResponseEntity<String> acceptStoreInvitation(@PathVariable("uuid") String uuid,
                                                        @RequestBody AcceptStoreInvitationRequestDto acceptStoreInvitationRequestDto) {
        Long storeId = acceptStoreInvitationRequestDto.storeId();
        paymentService.acceptStoreInvitation(uuid,storeId);
        return ResponseEntity.ok("선결제가 완료되었습니다");
    }

    @PostMapping("/invite/user/{uuid}")
    @Operation(summary = "가게 -> 유저 선결제 승인", description = "가게가 유저에 보낸 선결제 요청을 승인합니다")
    public ResponseEntity<String> acceptUserInvitation(@PathVariable("uuid") String uuid, @AuthenticationPrincipal CustomOAuth2User user) {
        Long userId = user.getUserId();
        paymentService.acceptUserInvitation(uuid,userId);
        return ResponseEntity.ok("선결제가 완료되었습니다");
    }

    @GetMapping("/mypayment")
    @Operation(summary = "내 선결제 내역 조회", description = "로그인 된 계정 선결제 내역 전체 조회합니다")
    public ResponseEntity<List<FindMyPaymentResponseDto>> findMyPayment(@AuthenticationPrincipal CustomOAuth2User user) {
        Long userId =user.getUserId();
        List<Payment> payments= paymentService.findAllPaymentByUserId(userId);
        List<FindMyPaymentResponseDto> findMyPaymentResponseDtos = payments.stream()
                .map(payment -> new FindMyPaymentResponseDto(
                        payment.getId(), payment.getQuantity(), payment.getUuidCode(), payment.getStatus()
                )).toList();
        return ResponseEntity.ok(findMyPaymentResponseDtos);
    }
}
