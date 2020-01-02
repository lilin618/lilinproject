package com.example.lilinproject.controller;

import com.example.lilinproject.pojo.User;
import com.example.lilinproject.service.UserService;
import com.example.lilinproject.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String registerPage(){
        return "register";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/UserLogin")
    @ResponseBody
    public String UserLogin(HttpServletRequest request, HttpSession session){
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        String msg = "error";
        try{
            User user = userService.findUserByUserName(username);
            if(user == null){
                msg = "NotFoundUser";
            }else{
                if(!user.getIsVaild().equals("Y")){
                    msg = "NotIsVaild";
                }else{
                    boolean loginFlag = new MD5Util().verify(pwd,user.getPassword());
                    if(loginFlag){
                        msg = "success";
                        session.setAttribute("userInfo",user);
                        session.setMaxInactiveInterval(1800);
                    }else {
                        msg = "fail";
                    }
                }
            }
        }catch (Exception e){
            log.error("登录异常，异常信息："+e);
        }
            return msg;
    }

    @PostMapping("/UserRegister")
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

    //检测用户名是否可用
    @PostMapping("/verifyUserName")
    @ResponseBody
    public String verifyUserName(HttpServletRequest request){
        String username = request.getParameter("username");
        User user = userService.findUserByUserName(username);
        String result = "fail";
        if(user != null){
            result = "ok";
        }
        return result;
    }
}
