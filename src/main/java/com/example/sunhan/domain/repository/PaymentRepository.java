package com.example.sunhan.domain.repository;

import com.example.sunhan.domain.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    public Optional<Payment> findByUuidCode(String uuidCode);

    public Optional<Payment> findByStore_Id(Long storeId);

    public Optional<Payment> findByUser_Id(Long userId);

    @Query("SELECT p FROM Payment p JOIN FETCH p.store WHERE p.uuidCode = :uuidCode")
    Optional<Payment> findByUuidCodeWithStore(String uuidCode);

    @Query("SELECT p FROM Payment p JOIN FETCH p.user WHERE p.uuidCode = :uuidCode")
    Optional<Payment> findByUuidCodeWithUser(String uuidCode);

    public List<Payment> findAllByUserId(Long userId);

    public List<Payment> findAllByStoreId(Long storeId);

    public boolean existsByUuidCode(String uuid);
}
