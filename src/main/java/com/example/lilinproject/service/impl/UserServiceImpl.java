package com.example.lilinproject.service.impl;

import com.example.lilinproject.pojo.User;
import com.example.lilinproject.resposity.UserResposity;
import com.example.lilinproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserResposity userResposity;

    @Override
    public User findUserByUserName(String username) {
        try{
            return userResposity.findByUsername(username);
        }catch (Exception e){
            log.error("【findUserByUserName】异常："+e);
            return null;
        }
    }

    @Override
    public String addUser(User user) {
        try{
            userResposity.save(user);
            return "ok";
        }catch (Exception e){
            log.error("注册用户异常："+e);
            return "fail";
        }
    }
}
