package com.example.lilinproject.resposity;

import com.example.lilinproject.pojo.PWQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PWQuestionResposity extends JpaRepository<PWQuestion,Integer> {
    PWQuestion findByUserId(long userid);

    @Query(value = "select a from PWQuestion a where a.userId=(select b.id from User b where b.username=?1)")
    PWQuestion getQuestionByUserName(String userName);
}
