package com.zust.EDP.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zust.EDP.dao.RequestDao;
import com.zust.EDP.dao.UserDao;
import com.zust.EDP.dto.Request;
import com.zust.EDP.entity.Trequest;
import com.zust.EDP.entity.Tuser;
import com.zust.EDP.service.RequestService;
import com.zust.EDP.util.Tools;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {
	int i;
	@Autowired
	private RequestDao requestDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private Tools tools;

	@Override
	public String doissueReceipt(Request request, HttpSession session) {
		// TODO Auto-generated method stub
		Trequest request2 = new Trequest();
		request2 = Request_Trequest(request, session);
		requestDao.saveRequest(request2);
		return "true";
	}

	@Override
	public List<Request> selectAddress(String select, int limit) {
		// TODO Auto-generated method stub
		List<Request> list2 = new ArrayList<Request>();
		List<Trequest> list = requestDao.findRequest(1, select, limit);
		for (int i = 0; i < list.size(); i++) {
			Trequest request = list.get(i);
			list2.add(i, Trequest_Request(request));
		}
		System.out.println(list.size());
		return list2;
	}

	@Override
	public Request Trequest_Request(Trequest request) {
		// TODO Auto-generated method stub
		Request request2 = new Request();
		request2.setAddress(request.getAddress());
		request2.setCreditLevel(request.getUser_requester_id().getCreditLevel());
		request2.setFreeDate(request.getFreeDate());
		request2.setIntroduce(request.getIntroduce());
		request2.setFromNum(request.getFromNum());
		request2.setRequestDate(tools.formatTime(request.getRequestDate()));
		request2.setRequesteruserId(request.getUser_requester_id().getUserId());
		request2.setRequesterusername(request.getUser_requester_id().getName());
		request2.setRequesteruserImage(request.getUser_requester_id().getImage());
		return request2;
	}

	@Override
	public Trequest Request_Trequest(Request request, HttpSession session) {
		// TODO Auto-generated method stub
		Tuser u = (Tuser) session.getAttribute("userMessage");
		Trequest request2 = new Trequest();
		request2.setAddress(request.getAddress());
		request2.setFreeDate(request.getFreeDate());
		request2.setIntroduce(request.getIntroduce());
		request2.setRequestDate(tools.getNowTime());
		request2.setFromNum(tools.makeFromNumByRandom("ES", i));
		i = i + 1;
		request2.setUser_requester_id(userDao.findUser_by_Id(u.getUserId()));
		return request2;
	}

	@Override
	public List<Request> putdown(int page, String address, int limit) {
		// TODO Auto-generated method stub
		List<Trequest> list = requestDao.findRequest(page, address, limit);
		List<Request> list2 = new ArrayList<Request>();
		for (int i = 0; i < list.size(); i++) {
			Trequest request = list.get(i);
			list2.add(i, Trequest_Request(request));
		}
		return list2;
	}
}
