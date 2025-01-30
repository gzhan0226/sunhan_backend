package com.example.sunhan.domain.service;

import com.example.sunhan.domain.domain.Order;
import com.example.sunhan.domain.domain.Store;
import com.example.sunhan.domain.domain.User;
import com.example.sunhan.domain.exception.NotFoundException;
import com.example.sunhan.domain.repository.OrderRepository;
import com.example.sunhan.domain.repository.StoreRepository;
import com.example.sunhan.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public void createOrder(Long storeId, Long userId, int quantity) {
       User user = userRepository.findById(userId)
               .orElseThrow(()->new NotFoundException("User Not Found"));
       Store store = storeRepository.findById(storeId)
               .orElseThrow(()->new NotFoundException("User Not Found"));

       Order order = Order.builder()
               .store(store)
               .user(user)
               .quantity(quantity)
               .build();

       orderRepository.save(order);
    }
}
