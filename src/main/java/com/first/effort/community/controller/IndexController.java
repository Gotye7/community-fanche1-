package com.first.effort.community.controller;

import com.first.effort.community.mapper.UserMapper;
import com.first.effort.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){
        //String header=request.getHeader("cookie");

        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
        }

        return"index";
    }
//    @GetMapping("/")
//    public String index(){
//        return "index";
//    }

}