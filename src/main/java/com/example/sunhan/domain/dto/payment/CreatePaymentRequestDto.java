package com.example.sunhan.domain.dto.payment;

import lombok.Builder;

public record CreatePaymentRequestDto(Long storeId, Long userId, int quantity) {
}
