package com.example.sunhan.domain.dto.payment.response;

public record FindUserInvitationResponseDto(Long id,
                                            Long storeId,
                                            String name,
                                            String phoneNumber,
                                            String address,
                                            int quantity) {
}
