package com.example.sunhan.domain.controller;


import com.example.sunhan.domain.service.CouponService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
@Tag(name = "Coupon", description = "QR 인증 생성 API")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/qr/{uuid}")
    public ResponseEntity<byte[]> createQRCode(@PathVariable("uuid") String uuidCode) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(couponService.createCoupon(uuidCode));
    }
}
