package com.example.sunhan.domain.repository;

import com.example.sunhan.domain.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository <Store, Long> {

    boolean existsByStoreCode(String storeCode);

    List<Store> findAllByUserId(Long userId);
}
