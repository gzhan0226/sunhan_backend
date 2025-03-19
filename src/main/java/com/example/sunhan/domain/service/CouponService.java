package com.example.sunhan.domain.service;

import com.example.sunhan.domain.domain.Coupon;
import com.example.sunhan.domain.domain.Payment;
import com.example.sunhan.domain.domain.User;
import com.example.sunhan.domain.exception.AllSoldOutException;
import com.example.sunhan.domain.exception.NotFoundException;
import com.example.sunhan.domain.repository.CouponRepository;
import com.example.sunhan.domain.repository.LockRepository;
import com.example.sunhan.domain.repository.PaymentRepository;
import com.example.sunhan.domain.repository.UserRepository;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.BarcodeFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CouponService {

    private final UserRepository userRepository;
    @Value("${app.base-url}")
    private String baseUrl;

    private final CouponRepository couponRepository;
    private final LockRepository lockRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void useCoupon(String uuidCode, Long userId, String storeCode) {
        Payment payment = paymentRepository.findByUuidCode(uuidCode)
                .orElseThrow(() ->new NotFoundException("Payment Not Found"));
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("User Not Found"));
        if (!payment.getStore().getStoreCode().equals(storeCode)) {
            throw new IllegalArgumentException("해당 지점 QR 코드가 아닙니다 지점별 코드를 다시 확인하세요");
        }
        try {
            lockRepository.getLock("coupon");
            if (payment.getQuantity() < 1) {
                throw new AllSoldOutException("남은 수량이 없습니다");
            } else {
                couponRepository.save(Coupon.builder()
                                .payment(payment)
                                .user(user)
                                .usedAt(LocalDateTime.now())
                                .build());
                payment.reduceQuantity();
            }
        } finally {
            lockRepository.releaseLock("coupon");
        }
    }

    @Transactional
    public byte[] createCoupon(String uuidCode)  {
        Payment payment = paymentRepository.findByUuidCode(uuidCode)
                .orElseThrow(() ->new NotFoundException("Payment Not Found"));
        String couponUrl = baseUrl + "/api/coupon/" + payment.getUuidCode(); //보안 기능 추가 필요
        try {
            return createQR(couponUrl);
        } catch(WriterException | IOException e) {
            throw new RuntimeException("QR 코드 생성 실패");
        }
    }

    public byte[] createQR(String url) throws WriterException, IOException {
        //qr 크기 설정
        int width = 200;
        int height = 200;

        // QR Code - BitMatrix: qr code 정보 생성
        BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE,width,height);

        try(ByteArrayOutputStream out = new ByteArrayOutputStream();){
            MatrixToImageWriter.writeToStream(bitMatrix,"PNG",out);
            return out.toByteArray();
        }
    }
}
