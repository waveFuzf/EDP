package com.zust.EDP.controller;

import com.zust.EDP.dto.Eveluate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/exit")
public class ExitController {
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public String exit(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession();
        System.out.println(httpServletRequest.getSession().toString());
        return "/user/login";
    }



}
