package com.example.sunhan.domain.dto.payment.response;

import com.example.sunhan.domain.domain.User;
import lombok.Builder;

@Builder
public record FindStoreInvitationResponseDto(Long id, User user, int quantity) {
}
