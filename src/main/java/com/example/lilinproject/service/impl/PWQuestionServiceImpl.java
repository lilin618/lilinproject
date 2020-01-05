package com.example.lilinproject.service.impl;

import com.example.lilinproject.pojo.PWQuestion;
import com.example.lilinproject.resposity.PWQuestionResposity;
import com.example.lilinproject.service.PWQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PWQuestionServiceImpl implements PWQuestionService {
    @Autowired
    private PWQuestionResposity pwQuestionResposity;
    @Override
    public PWQuestion getQuestion(long userId) {
        try{
            return pwQuestionResposity.findByUserId(userId);
        }catch (Exception e){
            log.error("【getQuestion】异常："+e);
            return null;
        }
    }

    @Override
    public String addQuestion(PWQuestion pwQuestion) {
        try{
            pwQuestionResposity.save(pwQuestion);
            return "ok";
        }catch (Exception e){
            log.error("【addQuestion】异常："+e);
            return "fail";
        }

    }

    @Override
    public PWQuestion getQuestionByUserName(String username) {
        try{
            return pwQuestionResposity.getQuestionByUserName(username);
        }catch (Exception e){
            log.error("【getQuestionByUserName】异常："+e);
            return null;
        }
    }
}
