package com.example.lilinproject.resposity;

import com.example.lilinproject.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResposity extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
