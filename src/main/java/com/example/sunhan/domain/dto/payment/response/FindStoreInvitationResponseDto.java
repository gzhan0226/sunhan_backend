package com.example.sunhan.domain.dto.payment.response;

import lombok.Builder;

@Builder
public record FindStoreInvitationResponseDto(Long id, Long userId, String nickname, String profileImg, int quantity) {
}
