package com.example.sunhan.domain.repository;

import com.example.sunhan.domain.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
