package com.example.sunhan.domain.domain;

import com.example.sunhan.domain.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne
    @JoinColumn(name = "user_id") //쿠폰 사용자 id
    private User user;

    @Column(name = "used_at")
    @DateTimeFormat
    private LocalDateTime usedAt;

    @Builder
    Coupon(Payment payment, User user, LocalDateTime usedAt) {
        this.user = user;
        this.payment = payment;
        this.usedAt = usedAt;
    }
}
