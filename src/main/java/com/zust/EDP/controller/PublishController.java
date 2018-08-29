package com.zust.EDP.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zust.EDP.dto.Dpublish;
import com.zust.EDP.dto.Publish;
import com.zust.EDP.service.PublishService;

@Controller
@RequestMapping("/task1")
public class PublishController {
	@Autowired
	private PublishService publishService;

	// 点击发布委托按钮
	@RequestMapping(value = "/issueone", method = RequestMethod.GET)
	public String issueAppointment() {
		return "issue_one";
	}

	// 点击第一种发布
	@RequestMapping(value = "/appointment", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> doissueAppointment(Dpublish publish, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		String message = publishService.doRegisterdoissueAppointment(publish, session);
		map.put("message", message);
		return map;
	}

	// 搜索
	@RequestMapping(value = "/selectAddress", method = RequestMethod.POST)
	public @ResponseBody List<Publish> selectAddress(HttpServletResponse response, String select, int limit) {
		return publishService.selectAddress(select, limit, null);
	}

	// 向下拉刷新
	@RequestMapping(value = "/putdown", method = RequestMethod.POST)
	public @ResponseBody List<Publish> putdown(HttpServletResponse response, String page, String select, String limit) {
		int page2 = Integer.parseInt(page);
		int limit1 = Integer.parseInt(limit);
		List<Publish> list = publishService.putdown(page2, select, limit1);
		System.out.println("页数：" + page2);
		return list;
	}

	// 点击写委托
	@RequestMapping(value = "/writing", method = RequestMethod.GET)
	public String writing() {
		return "entrust";
	}

}
