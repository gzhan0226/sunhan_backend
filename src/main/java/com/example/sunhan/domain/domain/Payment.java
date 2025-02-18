package com.example.sunhan.domain.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "uuid_code")
    private String uuidCode;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Builder
    Payment(Store store, User user, int quantity, String uuidCode, PaymentStatus status) {
        this.store = store;
        this.user = user;
        this.quantity = quantity;
        this.uuidCode = uuidCode;
        this.status = status;
    }

    public void reduceQuantity() {
        this.quantity -= 1;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updateStore(Store store) {
        this.store = store;
    }
    public void updateUser(User user) {

        this.user = user;
    }
    public void updateStatus(PaymentStatus status) {
        this.status = status;
    }
}
