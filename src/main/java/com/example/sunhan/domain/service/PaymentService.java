package com.example.sunhan.domain.service;

import com.example.sunhan.domain.domain.Payment;
import com.example.sunhan.domain.domain.PaymentStatus;
import com.example.sunhan.domain.domain.Store;
import com.example.sunhan.domain.domain.User;
import com.example.sunhan.domain.exception.InvalidInvitationException;
import com.example.sunhan.domain.exception.NotFoundException;
import com.example.sunhan.domain.repository.PaymentRepository;
import com.example.sunhan.domain.repository.StoreRepository;
import com.example.sunhan.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

    public String createStoreInvitation(Long userId, int quantity) { // 사용자 -> 가게
        User user = userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException("User Not Found"));

        String uuid = UUID.randomUUID().toString();

        while(true) {
            if (paymentRepository.existsByUuidCode(uuid)) {
                break;
            }
            else {
                uuid = UUID.randomUUID().toString();
            }
        }

        Payment payment = Payment.builder()
                .user(user)
                .quantity(quantity)
                .uuidCode(uuid)
                .status(PaymentStatus.WAITING)
                .build();
        paymentRepository.save(payment);
        return uuid;
    }

    public String createUserInvitation(Long storeId, int quantity) { // 가게 -> 사용자
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()->new NotFoundException("Store Not Found"));

        String uuid = UUID.randomUUID().toString();

        while(true) {
            if (paymentRepository.existsByUuidCode(uuid)) {
                break;
            }
            else {
                uuid = UUID.randomUUID().toString();
            }
        }

        Payment payment = Payment.builder()
                .store(store)
                .quantity(quantity)
                .uuidCode(uuid)
                .status(PaymentStatus.WAITING)
                .build();
        paymentRepository.save(payment);
        return uuid;
    }

    public void acceptStoreInvitation(String uuid, Long storeId) {
        Payment payment = paymentRepository.findByUuidCode(uuid)
                .orElseThrow(()->new NotFoundException("Invalid uuid"));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(()->new NotFoundException("Store Not Found"));

        if(payment.getStatus().equals(PaymentStatus.WAITING)) {
            payment.updateStore(store);
            payment.updateStatus(PaymentStatus.ACCEPTED);
        }
        else {
            throw new InvalidInvitationException("유효하지 않은 초대입니다");
        }
    }

    public void acceptUserInvitation(String uuid, Long userId) {
        Payment payment = paymentRepository.findByUuidCode(uuid)
                .orElseThrow(()->new NotFoundException("Invalid uuid"));

        User user = userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException("User Not Found"));

        if (payment.getStatus().equals(PaymentStatus.WAITING)) {
            payment.updateUser(user);
            payment.updateStatus(PaymentStatus.ACCEPTED);
        }
        else {
            throw new InvalidInvitationException("유효하지 않은 초대입니다");
        }
    }

   public Payment findUserInvitation(String uuid) {
        return paymentRepository.findByUuidCodeWithStore(uuid)
                .orElseThrow(()->new NotFoundException("Invalid uuid"));
   }

   public Payment findStoreInvitation(String uuid) {
        return paymentRepository.findByUuidCodeWithUser(uuid)
                .orElseThrow(()->new NotFoundException("Invalid uuid"));
   }
}
