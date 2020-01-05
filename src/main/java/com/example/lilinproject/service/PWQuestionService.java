package com.example.lilinproject.service;

import com.example.lilinproject.pojo.PWQuestion;

public interface PWQuestionService {
    PWQuestion getQuestion(long userId);

    String addQuestion(PWQuestion pwQuestion);

    PWQuestion getQuestionByUserName(String username);
}
