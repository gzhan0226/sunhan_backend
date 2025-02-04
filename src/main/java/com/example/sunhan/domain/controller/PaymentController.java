package com.example.sunhan.domain.controller;

import com.example.sunhan.domain.dto.payment.CreatePaymentRequestDto;
import com.example.sunhan.domain.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> createPayment(@ModelAttribute CreatePaymentRequestDto createPaymentRequestDto) {

        Long storeId = createPaymentRequestDto.storeId();
        Long userId = createPaymentRequestDto.userId();
        int quantity = createPaymentRequestDto.quantity();

        paymentService.createPayment(storeId, userId, quantity);

        return ResponseEntity.ok("선결제를 생성하였습니다");
    }
}
