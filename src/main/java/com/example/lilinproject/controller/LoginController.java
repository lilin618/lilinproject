package com.example.lilinproject.controller;

import com.example.lilinproject.pojo.User;
import com.example.lilinproject.resposity.UserResposity;
import com.example.lilinproject.service.UserService;
import com.example.lilinproject.utils.MD5Util;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String registerPage(){
        return "register";
    }

    @RequestMapping("/login")
    @ResponseBody
    public User login(HttpServletRequest request){
        String username = request.getParameter("username");
        return userService.findUserByUserName(username);
    }

    @RequestMapping("/UserRegister")
    @ResponseBody
    public String UserRegister(HttpServletRequest request){
        MD5Util md5Util = new MD5Util();
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        String birthDay = request.getParameter("birthDay");
        String address = request.getParameter("address");
        Map<String,String> params = md5Util.generate(pwd);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user = new User();
        user.setUsername(username);
        user.setPassword(params.get("MPWD"));
        user.setBirthDay(birthDay);
        user.setAddress(address);
        user.setCreateDate(simpleDateFormat.format(new Date()));
        user.setIsVaild("Y");
        user.setSalt(params.get("salt"));
        return userService.addUser(user);
    }
}
