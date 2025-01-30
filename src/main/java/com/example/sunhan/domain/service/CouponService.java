package com.example.sunhan.domain.service;

import com.example.sunhan.domain.domain.Coupon;
import com.example.sunhan.domain.domain.Payment;
import com.example.sunhan.domain.exception.AllSoldOutException;
import com.example.sunhan.domain.exception.NotFoundException;
import com.example.sunhan.domain.repository.CouponRepository;
import com.example.sunhan.domain.repository.LockRepository;
import com.example.sunhan.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CouponService {

    private final CouponRepository couponRepository;
    private final LockRepository lockRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void useCoupon(Long orderId) {
        Payment payment = paymentRepository.findById(orderId)
                .orElseThrow(() ->new NotFoundException("Order Not Found"));
        try {
            lockRepository.getLock("coupon");
            if (payment.getQuantity() < 1) {
                throw new AllSoldOutException("남은 수량이 없습니다");
            } else {
                couponRepository.save(Coupon.builder()
                                .payment(payment)
                                .usedAt(LocalDateTime.now())
                                .build());
                payment.reduceQuantity();
            }
        } finally {
            lockRepository.releaseLock("coupon");
        }
    }

}
