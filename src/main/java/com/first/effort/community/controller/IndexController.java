package com.first.effort.community.controller;

import com.first.effort.community.dto.PaginationDTO;
import com.first.effort.community.dto.QuestionDTO;
import com.first.effort.community.mapper.QuestionMapper;
import com.first.effort.community.mapper.UserMapper;
import com.first.effort.community.model.Question;
import com.first.effort.community.model.User;
import com.first.effort.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "2") Integer size){
        //String header=request.getHeader("cookie");

        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length!=0) {
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
        }

        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return"index";
    }
//    @GetMapping("/")
//    public String index(){
//        return "index";
//    }

}