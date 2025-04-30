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

    @Query("SELECT p FROM Payment p JOIN FETCH p.store WHERE p.user.id = :userId")
    public List<Payment> findAllByUserIdWithStore(Long userId);

    @Query("SELECT p FROM Payment p JOIN FETCH p.store s WHERE s.user.id = :userId")
    public List<Payment> findAllByStoreUserIdWithStore(Long userId);

    public boolean existsByUuidCode(String uuid);
}
