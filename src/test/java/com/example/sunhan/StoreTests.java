package com.example.sunhan;

import com.example.sunhan.domain.domain.Store;
import com.example.sunhan.domain.domain.User;
import com.example.sunhan.domain.repository.StoreRepository;
import com.example.sunhan.domain.repository.UserRepository;
import com.example.sunhan.domain.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StoreTests {


    @Autowired
    private StoreService storeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    private User donate;
    private User owner;

    @BeforeEach
    void setUp() {
        donate = User.builder()
                .nickname("donate person")
                .password("1234")
                .profileImg("test.png")
                .build();

        owner = User.builder()
                .nickname("store owner")
                .password("1234")
                .profileImg("test.png")
                .build();

        userRepository.save(donate);
        userRepository.save(owner);
    }

    @Test
    @DisplayName("가게 생성 테스트")
    @Transactional
    void createStore_ShouldSaveStore() {
        Long userId = owner.getId();
        //가게 생성
        for(int i=1;i<101;i++) {
            storeService.createStore(userId, "test store " + i, "12341234", "test address" +i);
        }

        //저장된 가게 확인
        List<Store> stores = storeRepository.findAll();
        assertEquals(100, stores.size());

        for(int i=0;i<100;i++) {
            System.out.println(stores.get(i).getName());
            System.out.println(stores.get(i).getStoreCode());
        }

    }



}