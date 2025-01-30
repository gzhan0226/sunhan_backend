package com.example.sunhan.domain.repository;

import com.example.sunhan.domain.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository <Coupon, Long> {
}
