package com.example.sunhan.domain.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Order {

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
    private Long quantity;

    @Builder
    Order(Store store, User user, Long quantity) {
        this.store = store;
        this.user = user;
        this.quantity = quantity;
    }

    void reduceQuantity() {
        this.quantity -= 1;
    }

    void updateQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
