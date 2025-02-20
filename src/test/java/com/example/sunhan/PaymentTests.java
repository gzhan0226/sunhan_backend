package com.example.sunhan;

import com.example.sunhan.domain.domain.*;
import com.example.sunhan.domain.exception.NotFoundException;
import com.example.sunhan.domain.repository.CouponRepository;
import com.example.sunhan.domain.repository.PaymentRepository;
import com.example.sunhan.domain.repository.StoreRepository;
import com.example.sunhan.domain.repository.UserRepository;
import com.example.sunhan.domain.service.CouponService;
import com.example.sunhan.domain.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentTests {

    @Autowired
    private CouponService couponService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    private Store store;
    private User donate;
    private User owner;

    @BeforeEach
    void setUp() {
        donate = User.builder()
                .username("donate person")
                .password("1234")
                .profileImg("test.png")
                .build();

        owner = User.builder()
                .username("store owner")
                .password("1234")
                .profileImg("test.png")
                .build();

        userRepository.save(donate);
        userRepository.save(owner);

        store = Store.builder()
                .name("test store")
                .address("test address")
                .phoneNumber("test number")
                .user(owner)
                .storeCode("111")
                .build();

        storeRepository.save(store);
    }

    @Test
    @DisplayName("가게 초대/수락 테스트")
    @Transactional
    void inviteStoreAndAccept_ShouldSavePayment() {
        Long userId = donate.getId();
        Long storeId = store.getId();

        //사용자 -> 가게 초대
        paymentService.createStoreInvitement(userId,100);

        //가게 초대 수락
        Payment payment1 = paymentRepository.findByUser_Id(userId)
                .orElseThrow(()->new NotFoundException("User Not Found"));
        paymentService.acceptStoreInvitement(payment1.getUuidCode(),storeId);

        //저장된 주문 확인
        List<Payment> payments = paymentRepository.findAll();
        assertEquals(1, payments.size());

        Payment payment = payments.get(0);
        System.out.println(payment.getStatus());
        assertEquals(storeId, payment.getStore().getId());
        assertEquals(userId, payment.getUser().getId());
        assertEquals(100, payment.getQuantity());
        assertEquals(PaymentStatus.ACCEPTED, payment.getStatus());

    }

    @Test
    @DisplayName("사용자 초대/수락 테스트")
    @Transactional
    void inviteUserAndAccept_ShouldSavePayment() {
        Long userId = donate.getId();
        Long storeId = store.getId();

        //가게 -> 사용자 초대
        paymentService.createUserInvitement(storeId,100);

        //사용자 초대 수락
        Payment payment1 = paymentRepository.findByStore_Id(storeId)
                .orElseThrow(()->new NotFoundException("Store Not Found"));
        paymentService.acceptUserInvitement(payment1.getUuidCode(),userId);

        //저장된 주문 확인
        List<Payment> payments = paymentRepository.findAll();
        assertEquals(1, payments.size());

        Payment payment = payments.get(0);
        System.out.println(payment.getStatus());
        assertEquals(storeId, payment.getStore().getId());
        assertEquals(userId, payment.getUser().getId());
        assertEquals(100, payment.getQuantity());
        assertEquals(PaymentStatus.ACCEPTED, payment.getStatus());

    }

    @Test
    @DisplayName("쿠폰 사용 테스트")
    @Transactional
    void useCoupon_ShouldSaveCouponAndDecreaseQuantity() {
        Long userId = donate.getId();
        Long storeId = store.getId();

        //주문 생성
        paymentService.createPayment(storeId,userId,100);
        Payment payment = paymentRepository.findAll().get(0);

        //쿠폰 사용
        for (int i=0;i<100; i++) {
            couponService.useCoupon(payment.getId(), "111");
        }

        List<Coupon> coupons = couponRepository.findAll();

        //남은 수량 확인
        assertEquals(0, payment.getQuantity());
        assertEquals(100, coupons.size());

    }

}
