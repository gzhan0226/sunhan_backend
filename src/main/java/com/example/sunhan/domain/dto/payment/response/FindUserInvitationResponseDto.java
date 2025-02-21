package com.example.sunhan.domain.dto.payment.response;

import com.example.sunhan.domain.domain.Store;

public record FindUserInvitationResponseDto(Long id, Store store, int quantity) {
}
