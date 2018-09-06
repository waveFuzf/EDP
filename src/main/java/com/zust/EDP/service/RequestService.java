package com.zust.EDP.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.zust.EDP.dto.Request;
import com.zust.EDP.entity.Trequest;

public interface RequestService {
	public String doissueReceipt(Request request, HttpSession session);

	public List<Request> selectAddress(String select, int limit, Integer userId);

	public Request Trequest_Request(Trequest request);

	public Trequest Request_Trequest(Request request, HttpSession session);

	public List<Request> putdown(int page, String address, int limit,Integer userid);
}
