package com.example.sunhan.domain.domain;

import jakarta.persistence.*;
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

    @Builder
    Payment(Store store, User user, int quantity) {
        this.store = store;
        this.user = user;
        this.quantity = quantity;
    }

    public void reduceQuantity() {
        this.quantity -= 1;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }
}
