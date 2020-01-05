package com.example.lilinproject.controller;

import com.example.lilinproject.pojo.PWQuestion;
import com.example.lilinproject.pojo.User;
import com.example.lilinproject.service.PWQuestionService;
import com.example.lilinproject.service.UserService;
import com.example.lilinproject.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    @Autowired
    private PWQuestionService pwQuestionService;

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
        String email = request.getParameter("email");
        Map<String,String> params = md5Util.generate(pwd);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user = new User();
        user.setUsername(username);
        user.setPassword(params.get("MPWD"));
        user.setBirthDay(birthDay);
        user.setAddress(address);
        user.setEmail(email);
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

    //设置密保问题
    @PostMapping("/addQuestion")
    @ResponseBody
    public String addQuestion(HttpServletRequest request,HttpSession session){
        String question = request.getParameter("question");
        String answer = request.getParameter("answer");
        String userName = request.getParameter("username");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user = null;
        PWQuestion pwQuestion = new PWQuestion();
        String result = "fail";
        try{
            user = userService.findUserByUserName(userName);
            if(user == null){
                result = "无此用户";
            }else {
                pwQuestion.setAnswer(answer);
                pwQuestion.setQuestion(question);
                pwQuestion.setIsvaild("Y");
                pwQuestion.setUserId(user.getId());
                pwQuestion.setCreateDate(simpleDateFormat.format(new Date()));
                result = pwQuestionService.addQuestion(pwQuestion);
            }
        }catch (Exception e){
            log.error("【Controller->addQuestion】异常："+e);
        }
        return result;
    }

    //获取密保问题
    @PostMapping("/getQuestion")
    @ResponseBody
    public PWQuestion getQuestion(HttpServletRequest request){
        String username = request.getParameter("username");
        return pwQuestionService.getQuestionByUserName(username);
    }
    //验证密保问题
    @PostMapping("/verifyQuestion")
    @ResponseBody
    public String verifyQuestion(HttpServletRequest request){
        String username = request.getParameter("username");
        String answer = request.getParameter("answer");
        PWQuestion pwQuestion = null;
        pwQuestion = pwQuestionService.getQuestionByUserName(username);
        if (pwQuestion != null){
            if (pwQuestion.getAnswer().equals(answer)){
                return "ok";
            }else {
                return "fail";
            }
        }else {
            return "fail";
        }
    }
    //生成四位随机验证码
    @GetMapping("/getCode")
    @ResponseBody
    public String getCode(HttpSession session){
        String verificationCode = String.valueOf((int)((Math.random()*9+1)*1000));
        session.setAttribute("code",verificationCode);
//        session.setMaxInactiveInterval(300);
        return verificationCode;
    }

    @GetMapping("/getSession")
    @ResponseBody
    public String getSession(HttpSession session){
        User user = null;
        try{
            user = (User)session.getAttribute("userInfo");
            String code = (String) session.getAttribute("code");
            return user.toString()+"---------"+code;
        }catch (NullPointerException e){
            log.info("session is null");
            return "session is null";
        }
    }

}
