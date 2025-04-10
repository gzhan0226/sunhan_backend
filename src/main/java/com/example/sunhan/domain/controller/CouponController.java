package com.example.sunhan.domain.controller;


import com.example.sunhan.domain.dto.coupon.request.ReadQRCodeRequestDto;
import com.example.sunhan.domain.service.CouponService;
import com.example.sunhan.global.auth.oauth.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
@Tag(name = "Coupon", description = "QR 인증 생성 API")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/qr/{uuid}")
    public ResponseEntity<byte[]> createQRCode(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(couponService.createCoupon(uuid));
    }

    @PostMapping("/qr/{uuid}")
    public ResponseEntity<String> readQRCode(@PathVariable("uuid") String uuid, @RequestBody ReadQRCodeRequestDto readQRCodeRequestDto,
                                             @AuthenticationPrincipal CustomOAuth2User user) {
        Long userId = user.getUserId();
        String storeCode = readQRCodeRequestDto.storeCode();
        couponService.useCoupon(uuid,userId,storeCode);
        return ResponseEntity.ok().body("결제 완료!");
    }
}
