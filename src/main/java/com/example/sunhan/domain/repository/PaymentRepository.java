package com.example.sunhan.domain.repository;

import com.example.sunhan.domain.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    public Optional<Payment> findByUuidCode(String uuidCode);

    public Optional<Payment> findByStore_Id(Long storeId);

    public Optional<Payment> findByUser_Id(Long userId);
}
