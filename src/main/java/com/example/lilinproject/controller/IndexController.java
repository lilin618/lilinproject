package com.example.lilinproject.controller;

import com.example.lilinproject.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping("indexPage")
    public String indexPage(HttpSession session, Model model){
        User user = null;
        try{
            user = (User)session.getAttribute("userInfo");
            model.addAttribute("userName",user.getUsername());
        }catch (NullPointerException e){
        }
        return "index";
    }
}
