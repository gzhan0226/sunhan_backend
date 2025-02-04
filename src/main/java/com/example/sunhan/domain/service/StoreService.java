package com.example.sunhan.domain.service;

import com.example.sunhan.domain.domain.Store;
import com.example.sunhan.domain.domain.User;
import com.example.sunhan.domain.exception.NotFoundException;
import com.example.sunhan.domain.repository.StoreRepository;
import com.example.sunhan.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public void createStore(Long userId, String name, String phoneNumber, String address) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("User Not Found"));

        String storeCode = generateRandomStoreCode(6);

        while(true) {
            if (!isStoreCodeValid(storeCode)) {
                break;
            }
            else {
                storeCode = generateRandomStoreCode(6);
            }
        }

        storeRepository.save(Store.builder()
                .name(name)
                .user(user)
                .phoneNumber(phoneNumber)
                .address(address)
                .storeCode(storeCode)
                .build());

    }

    public String generateRandomStoreCode(int codeLength) {
        Random random = new Random();
        StringBuffer storeCode = new StringBuffer();

        while(storeCode.length() < codeLength) {
            if (random.nextBoolean()) {
                storeCode.append((char)((int)(random.nextInt(26))+65));
            }
            else {
                storeCode.append(random.nextInt(10));
            }
        }

        return storeCode.toString();
    }

    public boolean isStoreCodeValid (String storeCode) {
        return storeRepository.existsByStoreCode(storeCode);
    }
}
