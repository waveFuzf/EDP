package com.zust.EDP.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zust.EDP.service.MessageService;

@Controller
@RequestMapping("/MM")
public class MessageController {
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public @ResponseBody String message(HttpServletResponse response,String messageId){
		Integer a =Integer.parseInt(messageId);
        messageService.readmessage(a);
        return "{\"state\":\"success\"}";
	}
}
