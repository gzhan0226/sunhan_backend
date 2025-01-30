package com.example.sunhan.domain.repository;

import com.example.sunhan.domain.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
