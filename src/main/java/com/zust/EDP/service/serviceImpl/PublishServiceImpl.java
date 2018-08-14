package com.zust.EDP.service.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zust.EDP.dao.ExpressDao;
import com.zust.EDP.dao.PublishDao;
import com.zust.EDP.dao.UserDao;
import com.zust.EDP.dto.Dpublish;
import com.zust.EDP.dto.Publish;
import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Tpublish;
import com.zust.EDP.entity.Tuser;
import com.zust.EDP.service.ExpressService;
import com.zust.EDP.service.PublishService;
import com.zust.EDP.util.Tools;

@Service
@Transactional
public class PublishServiceImpl implements PublishService {
	double longitude;
	double latitude;
	int i;
	@Autowired
	private PublishDao publishDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ExpressDao expressDao;
	@Autowired
	private Tools tolls;
	@Autowired
	private ExpressService expressService;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	@Override
	public List<Publish> selectAddress(String select, int limit) {
		// TODO Auto-generated method stub
		List<Publish> list2 = new ArrayList<Publish>();
		List<Texpress> list = publishDao.findPublish(1, select, limit);
		System.out.println("sss"+list.size());
		for(int i=0;i<list.size();i++) {
			Texpress express = list.get(i);
			Publish publish2 = Tpublish_Publish(express);
			publish2.setDistance(distance(express,longitude,latitude));
			if(publish2.getDistance()>300000) {
				System.out.println("失效的publishId"+publish2.getPublishId()+" "+publish2.getDistance());
				continue;
			}
			Calendar c2 = Calendar.getInstance();
			c2.setTime(express.getPublishId().getTakeDate());
			Calendar c1 = Calendar.getInstance();
			c1.setTime(tolls.getNowTime());
//			long difference=c2.getTimeInMillis()-c1.getTimeInMillis();
//			System.out.println("c1.date"+express.getPublishId().getPublishDate());
//			System.out.println("c2.date"+tolls.getNowTime());
//			System.out.println("c2.getTimeInMillis()"+c2.getTimeInMillis());
//			System.out.println("c1.getTimeInMillis()"+c1.getTimeInMillis());
//			long minute=difference/(60*1000);
//			System.out.println("+++++++minute"+minute);
//			//失效时间
			System.out.println("c2.getTimeInMillis()"+c2.getTimeInMillis());
			System.out.println("c1.getTimeInMillis()"+c1.getTimeInMillis());
			if(c1.getTimeInMillis()>c2.getTimeInMillis()) {
				System.out.println("失效的publishId"+publish2.getPublishId());
				publishDao.updatestate(express.getPublishId().getPublishId(),5);
				continue;
			}
		    list2.add(publish2);
     	}
		return list2;	
	}


	public Publish Tpublish_Publish(Texpress express) {
		Publish publish2 = new Publish();
		publish2.setAddress(express.getAddress());
		// publish2.setCode(express.getCode());
		publish2.setCompany(express.getCompany());
		// publish2.setCreditLevel(express.getPublishId().getUser_publisher_id().getCreditLevel());
		// publish2.setExpressId(express.getExpressId());
		publish2.setIntegral(express.getPublishId().getIntegral());
		publish2.setFromNum(express.getPublishId().getFromNum());
		System.out.println(express.getPublishId().getPublishDate());
		publish2.setPublishDate(tolls.formatTime(express.getPublishId().getPublishDate()));
		publish2.setPublishId(express.getPublishId().getPublishId());
		publish2.setPublisheruserId(express.getPublishId().getUser_publisher_id().getUserId());
		publish2.setPublisherusername(express.getPublishId().getUser_publisher_id().getName());
		publish2.setPublisheruserimage(express.getPublishId().getUser_publisher_id().getImage());
		publish2.setRequirement(express.getPublishId().getRequirement());
		publish2.setSize(express.getSize());
		// publish2.setState(express.getPublishId().getState());
		System.out.println("xxxxxxx");
		publish2.setTakeDate(tolls.formatTime(express.getPublishId().getTakeDate()));
		publish2.setPublishDate(tolls.formatTime(express.getPublishId().getPublishDate()));
		System.out.println(publish2.getTakeDate());
		publish2.setTip(express.getTip());
		publish2.setType(express.getType());
		// publish2.setLatitude(express.getLatitude());
		// publish2.setLongitude(express.getLongitude());
		return publish2;
	}

	@Override
	public Tpublish Publish_Tpublish(Publish publish, HttpSession session) {
		// TODO Auto-generated method stub
		Tpublish publish2 = new Tpublish();
		publish2.setFromNum(tolls.makeFromNumByRandom("PH", i));
		i = i + 1;
		publish2.setPublishDate(tolls.getNowTime());
		publish2.setPublishId(publish.getPublishId());
		publish2.setRequirement(publish.getRequirement());
		publish2.setState(2);
		publish2.setTakeDate(null);
		publish2.setUser_publisher_id(userDao.findUser_by_Id(publish.getPublisheruserId()));
		return null;
	}

	@Override
	public String doRegisterdoissueAppointment(Dpublish publish, HttpSession session) {
		// TODO Auto-generated method stub
		List<Texpress> e = expressDao.findexpress_by_companyandcode(publish.getCompany(), publish.getCode());
		Tuser u = (Tuser) session.getAttribute("userMessage");
		if (e.isEmpty()) {
			Texpress express = new Texpress();
			System.out.println("" + publish.getAddress());
			publish.setState(2);
			// express = expressService.Dpublish_Texpress(publish, session);
			Tuser user = userDao.findUser_by_Id(u.getUserId());
			System.out.println("用户积分" + user.getIntegral());
			System.out.println("输入积分" + publish.getIntegral());
			if (user.getIntegral() < publish.getIntegral()) {
				return "falseTwo";
			} else {
				express = expressService.Dpublish_Texpress(publish, session);
				u.setIntegral(u.getIntegral() - publish.getIntegral());
				userDao.updateuser(u.getUserId(), u);
				expressDao.saveExpress(express);
				return "true";
			}
		} else {
			return "falseOne";
		}
	}

	@Override
	public List<Publish> putdown(int page, String address, int limit) {
		// TODO Auto-generated method stub
		List<Publish> list2 = new ArrayList<Publish>();
		List<Texpress> list = publishDao.findPublish(page, address, limit);
		for (int i = 0; i < list.size(); i++) {
			Texpress express = list.get(i);
			Publish publish2 = Tpublish_Publish(express);
			publish2.setDistance(distance(express, longitude, latitude));
			list2.add(publish2);
		}
		return list2;
	}

	// 已知两点经纬度算距离
	@Override
	public double distance(Texpress express, Double longitude, Double latitude) {
		// TODO Auto-generated method stub
		double EARTH_RADIUS = 6378.137;
		double radLat1 = rad(express.getLatitude());
		double radLat2 = rad(latitude);
		double a = radLat1 - radLat2;
		double b = rad(express.getLongitude()) - rad(longitude);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
		s = s * 1000;
		return s;
	}

	@Override
	public void savelalo(double a, double b) {
		// TODO Auto-generated method stub
		longitude = a;
		latitude = b;
	}

	@Override
	public List<Publish> findpublishunsave(HttpSession session) {
		// TODO Auto-generated method stub
		Tuser u = (Tuser) session.getAttribute("userMessage");
		System.out.println("找到暂存usetId" + u.getUserId());
		List<Publish> list2 = new ArrayList<Publish>();
		List<Texpress> list = publishDao.find_by_id(u.getUserId());
		System.out.println("找到暂存" + list.size());
		for (int i = 0; i < list.size(); i++) {
			Publish publish = new Publish();
			publish = Tpublish_Publish(list.get(i));
			list2.add(i, publish);
		}
		return list2;
	}

	@Override
	public String temporary(Dpublish publish, HttpSession session) {
		// TODO Auto-generated method stub
		List<Texpress> e = expressDao.findexpress_by_companyandcode(publish.getCompany(), publish.getCode());
		if (e.isEmpty()) {
			Texpress express = new Texpress();
			publish.setState(1);
			express = expressService.Dpublish_Texpress(publish, session);
			expressDao.saveExpress(express);
			return "true";
		} else {
			return "flase";
		}
	}
	
	@Override
	public String deletepublish(Integer publishId) {
		// TODO Auto-generated method stub
		publishDao.updatestate(publishId, 6);
		return "true";
	}


}
