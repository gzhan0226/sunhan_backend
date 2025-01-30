package com.example.sunhan.domain.repository;

import com.example.sunhan.domain.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
