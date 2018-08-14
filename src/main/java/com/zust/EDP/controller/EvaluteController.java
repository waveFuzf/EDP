package com.zust.EDP.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zust.EDP.dto.Eveluate;
import com.zust.EDP.service.EvaluateService;

@Controller
@RequestMapping("/evalute")
public class EvaluteController {
	@Autowired
	private EvaluateService evaluateService;
	
	@RequestMapping(value = "/eva",method = RequestMethod.POST)
	public @ResponseBody Map<String,String> doissueAppointment(Eveluate evaluate ,HttpSession session) {
		Map<String, String> map=new HashMap<String,String>();
		String message = evaluateService.evaluate(evaluate, session);
		map.put("message", message);
		System.out.println("第一种发布"+message);
		return map;	
	}	

}
