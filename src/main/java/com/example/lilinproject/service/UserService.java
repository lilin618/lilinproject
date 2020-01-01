package com.example.lilinproject.service;

import com.example.lilinproject.pojo.User;

public interface UserService {
    User findUserByUserName(String username);
    String addUser(User user);
}
