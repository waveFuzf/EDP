package com.zust.EDP.controller;

import com.zust.EDP.dao.UserDao;
import com.zust.EDP.dto.Eveluate;
import com.zust.EDP.entity.Tuser;
import com.zust.EDP.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/exit")
public class ExitController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public String exit(HttpServletRequest httpServletRequest) {
        Tuser u=(Tuser)httpServletRequest.getSession().getAttribute("userMessage");
        userService.updateState(u.getUserId(),0);
        httpServletRequest.getSession().invalidate();
        return "/user/login";
    }



}
