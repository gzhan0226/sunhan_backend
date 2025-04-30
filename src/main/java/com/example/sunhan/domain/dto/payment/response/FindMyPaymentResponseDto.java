package com.example.sunhan.domain.dto.payment.response;

import com.example.sunhan.domain.domain.PaymentStatus;

public record FindMyPaymentResponseDto(Long id, int quantity, String uuid, PaymentStatus status,
                                       String name, String phoneNumber, String address) {
}
