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
import com.zust.EDP.dto.Request;
import com.zust.EDP.service.PublishService;
import com.zust.EDP.service.RequestService;

@Controller
@RequestMapping("/task")
public class RequestController {
	@Autowired
	private RequestService requestService;
	@Autowired
	private PublishService publishService;

	// 点击发布收件按钮
	@RequestMapping(value = "/receipt", method = RequestMethod.GET)
	public String issueReceipt() {
		return "Issue_two";
	}

	// 点击第一种暂存
	@RequestMapping(value = "/temporary", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> temporary(Dpublish publish, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		String message = publishService.temporary(publish, session);
		map.put("message", message);
		return map;
	}

	// 点击第二种发布
	@RequestMapping(value = "/issueReceipt", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> doissueReceipt(Request request, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		String message = requestService.doissueReceipt(request, session);
		map.put("message", message);
		return map;
	}

	// 搜索
	@RequestMapping(value = "/selectAddress", method = RequestMethod.POST)
	public @ResponseBody List<Request> selectAddress(HttpServletResponse response, String select, int limit) {
		return requestService.selectAddress(select, limit);
	}

	// 向下拉刷新
	@RequestMapping(value = "/putdown", method = RequestMethod.POST)
	public @ResponseBody List<Request> putdown(HttpServletResponse response, int page, String select, int limit) {
		List<Request> list = requestService.putdown(page, select, limit);
		return list;
	}

	// 点击请求
	@RequestMapping(value = "/clickrequest", method = RequestMethod.POST)
	public @ResponseBody List<Publish> clickrequest(HttpSession session) {
		return publishService.findpublishunsave(session);
	}

}
