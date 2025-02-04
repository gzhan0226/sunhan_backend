package com.example.sunhan.domain.service;

import com.example.sunhan.domain.domain.Payment;
import com.example.sunhan.domain.domain.Store;
import com.example.sunhan.domain.domain.User;
import com.example.sunhan.domain.exception.NotFoundException;
import com.example.sunhan.domain.repository.PaymentRepository;
import com.example.sunhan.domain.repository.StoreRepository;
import com.example.sunhan.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public void createPayment(Long storeId, Long userId, int quantity) {

        User user = userRepository.findById(userId)
               .orElseThrow(()->new NotFoundException("User Not Found"));
        Store store = storeRepository.findById(storeId)
               .orElseThrow(()->new NotFoundException("Store Not Found"));

        Payment payment = Payment.builder()
               .store(store)
               .user(user)
               .quantity(quantity)
               .build();

        paymentRepository.save(payment);
    }
}
