package com.example.lilinproject;

import com.example.lilinproject.utils.MD5Util;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Random;

@SpringBootTest
public class LilinprojectApplicationTests {

    MD5Util md5Util = new MD5Util();

    @Test
    public void test1(){
        String str = "hello";
        Map<String,String> mStr = md5Util.generate(str);
        System.out.println(mStr.toString());
        System.out.println(md5Util.verify("hello", mStr.get("MPWD")));
    }

    @Test
    public void test2(){
    }


}
