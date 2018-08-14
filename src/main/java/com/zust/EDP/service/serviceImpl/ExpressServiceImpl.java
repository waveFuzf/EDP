package com.zust.EDP.service.serviceImpl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zust.EDP.dao.PublishDao;
import com.zust.EDP.dao.UserDao;
import com.zust.EDP.dto.Dpublish;
import com.zust.EDP.dto.Publish;
import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Tpublish;
import com.zust.EDP.entity.Tuser;
import com.zust.EDP.service.ExpressService;
import com.zust.EDP.util.Tools;

@Service
@Transactional
public class ExpressServiceImpl implements ExpressService {
	int i = 0;
	@Autowired
	private PublishDao publishDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private Tools tools;

	@Override
	public Texpress Express_Texpress(Publish publish) {
		Texpress express = new Texpress();
		express.setAddress(publish.getAddress());
		// express.setCode(publish.getCode());
		express.setCompany(publish.getCompany());
		// express.setLatitude(publish.getLatitude());
		// express.setLongitude(publish.getLongitude());
		express.setPublishId(publishDao.findPublish_by_Id(publish.getPublishId()));
		express.setSize(publish.getSize());
		express.setTip(publish.getTip());
		express.setType(publish.getType());
		return express;
	}

	@Override
	public Texpress Dpublish_Texpress(Dpublish publish, HttpSession session) {
		// TODO Auto-generated method stub
		Tuser u = new Tuser();
		u = (Tuser) session.getAttribute("userMessage");
		Texpress express = new Texpress();
		Tpublish publish2 = new Tpublish();
		express.setAddress(publish.getAddress());
		express.setCode(publish.getCode());
		express.setCompany(publish.getCompany());
		express.setLatitude(publish.getLatitude());
		express.setLongitude(publish.getLongitude());
		express.setPublishId(publish2);
		publish2.setFromNum(tools.makeFromNumByRandom("PH", i));
		i = i + 1;
		publish2.setIntegral(publish.getIntegral());
		publish2.setPublishDate(tools.getNowTime());
		publish2.setRequirement(publish.getRequirement());
		System.out.println("限制条件：" + publish.getRequirement());
		publish2.setState(publish.getState());
		publish2.setTakeDate(tools.getDate(publish.getTakeDate()));
		System.out.println("takeDate="+tools.getDate(publish.getTakeDate()));
		publish2.setUser_publisher_id(userDao.findUser_by_Id(u.getUserId()));
		publishDao.savePublish(publish2);
		express.setSize(publish.getSize());
		express.setTip(publish.getTip());
		express.setType(publish.getType());
		return express;
	}

}
