package com.example.sunhan.domain.service;

import com.example.sunhan.domain.domain.Coupon;
import com.example.sunhan.domain.repository.CouponRepository;
import com.example.sunhan.domain.repository.LockRepository;
import com.example.sunhan.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final LockRepository lockRepository;
    private final OrderRepository orderRepository;

}
