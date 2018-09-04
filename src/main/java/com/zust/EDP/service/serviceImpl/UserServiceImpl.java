package com.zust.EDP.service.serviceImpl;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zust.EDP.dao.EvaluateDao;
import com.zust.EDP.dao.MessageDao;
import com.zust.EDP.dao.PublishDao;
import com.zust.EDP.dao.RecordDao;
import com.zust.EDP.dao.RequestDao;
import com.zust.EDP.dao.UserDao;
import com.zust.EDP.dto.Home;
import com.zust.EDP.dto.Homeuser;
import com.zust.EDP.dto.Message;
import com.zust.EDP.dto.Mytake;
import com.zust.EDP.dto.Publish;
import com.zust.EDP.dto.Request;
import com.zust.EDP.dto.Unread;
import com.zust.EDP.dto.User;
import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Tmessage;
import com.zust.EDP.entity.Trequest;
import com.zust.EDP.entity.Tuser;
import com.zust.EDP.service.MessageService;
import com.zust.EDP.service.PublishService;
import com.zust.EDP.service.RecordService;
import com.zust.EDP.service.RequestService;
import com.zust.EDP.service.UserService;
import com.zust.EDP.util.Tools;
import com.zust.EDP.util.VerificationCode;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private EvaluateDao evaluateDao;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private PublishDao publishDao;
	@Autowired
	private RequestDao requestDao;
	@Autowired
	private RecordDao recordDao;
	@Autowired
	private PublishService publishService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private RequestService requestService;
	@Autowired
	private RecordService recordService;
	@Autowired
	private Tools tool;

	@Override
	public String doRegister(Tuser user, HttpSession session, String imageIcon) {
		// TODO Auto-generated method stub
		List<Tuser> u = userDao.findUserTel(user);
		user.setName("user"+user.getTel());
		user.setTimes(0);
		String icon = (String) session.getAttribute("imageIcon");
		if (icon.equals(imageIcon)) {
			if (u.isEmpty()) {
				userDao.saveUser(user);
				return "true";
			} else {
				return "falseOne";
			}
		} else
			return "falseTwo";
	}

	@Override
	public Map<String, Object> doLogin(Tuser user, HttpSession session) {
		List<Tuser> u = userDao.findUserTel(user);
		Map<String, Object> map = new HashMap<String, Object>();
		if (u.isEmpty()) {
			map.put("isLogin", "falseTwo");
			return map;
		} else if (u.get(0).getPassword().equals(user.getPassword())) {
			session.setAttribute("userMessage", u.get(0));
			System.out.println("*****************"+u.get(0));
			map.put("isLogin", "true");
			Map<String, String> mapp = new HashMap<String, String>();
			mapp.put("tel", u.get(0).getTel());
			mapp.put("name", u.get(0).getName());
			mapp.put("id", String.valueOf(u.get(0).getUserId()));
			System.out.println("当前登录id为" + String.valueOf(u.get(0).getUserId()));
			mapp.put("image", u.get(0).getImage());
			mapp.put("integral", String.valueOf(u.get(0).getIntegral()));
			map.put("userMessage", mapp);
			return map;
		} else {
			map.put("isLogin", "falseOne");
			return map;
		}
	}

	@Override
	public void getImageIcon(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		VerificationCode verificationCode = new VerificationCode();
		verificationCode.doGet(request, response);
	}

	@Override
	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		getImageIcon(request, response);
	}

	@Override
	public User Tuser_User(Tuser user) {
		// TODO Auto-generated method stub
		User user2 = new User();
		user2.setAddress(user.getAddress());
		user2.setCreditLevel(user.getCreditLevel());
		user2.setEvaluatedid(null);
		user2.setEvaluateduserid(null);
		user2.setEvaluatedusername(null);
		user2.setImage(user.getImage());
		user2.setIntegral(user.getIntegral());
		user2.setMessageId(null);
		user2.setName(user.getName());
		user2.setPassword(user.getPassword());
		user2.setPublishId(null);
		user2.setRecordid(null);
		user2.setRequesterid(null);
		user2.setSendeduserid(null);
		user2.setSendedusername(null);
		user2.setSex(user.getSex());
		user2.setState(user.getState());
		user2.setTel(user.getTel());
		user2.setTimes(user.getTimes());
		user2.setUserId(user.getUserId());
		return user2;
	}

	@Override
	public Tuser User_Tuser(User user) {
		// TODO Auto-generated method stub
		Tuser user2 = new Tuser();
		user2.setAddress(user.getAddress());
		user2.setCreditLevel(user.getCreditLevel());
		user2.setEvaluated_id(evaluateDao.findevaluate_by_id(user.getEvaluatedid()));
		user2.setImage(user.getImage());
		user2.setIntegral(user.getIntegral());
		user2.setMessage_id(messageDao.findmessage_by_id(user.getMessageId()));
		user2.setName(user.getName());
		user2.setPassword(user.getPassword());
		user2.setPublisher_id(publishDao.findPublish_by_Id(user.getPublishId()));
		user2.setRequester_id(requestDao.findRequest_by_id(user.getRequesterid()));
		user2.setSended_id(recordDao.findRecord_by_recordid(user.getRecordid()));
		user2.setSex(user.getSex());
		user2.setState(user.getState());
		user2.setTel(user.getTel());
		user2.setTimes(user.getTimes());
		user2.setUserId(user.getUserId());
		return user2;
	}

	@Override
	public List<Homeuser> homeuser() {
		// TODO Auto-generated method stub
		List<Homeuser> list2 = new ArrayList<Homeuser>();
		List<Tuser> list = userDao.findtop3_p();
		for (int i = 0; i < list.size(); i++) {
			User user = new User();
			Homeuser u = new Homeuser();
			user = Tuser_User(list.get(i));
			u.setCount(userDao.count("p", list.get(i)));
			u.setCreditLevel(user.getCreditLevel());
			u.setImage(user.getImage());
			u.setName(user.getName());
			u.setTel(user.getTel());
			u.setUserId(user.getUserId());
			list2.add(u);
		}
		System.out.println("长度" + list2.size());
		return list2;
	}

	@Override
	public Home loading(Integer userId) {
		// TODO Auto-generated method stub
		List<Homeuser> list3 = homeuser();
		System.out.println("findtip3OK");
		List<Message> list4 = messageService.findmessage(userId);
		System.out.println("messageServiceOk");
		List<Publish> list1 = publishService.selectAddress("", 10,userId);
		System.out.println("publishService");
		List<Request> list2 = requestService.selectAddress("", 10);
		System.out.println("publishService2");
		List<Unread> list5 = recordService.Trecord_Uread(userId);
		Home home = new Home();
		home.setMessage(list4);
		home.setPublish(list1);
		home.setRequest(list2);
		home.setMessage(list4);
		home.setUser(list3);
		home.setUnread(list5);
		return home;
	}

	@Override
	public Map<String, Object> updateUserMessage(Tuser tuser, HttpSession session) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userDao.update(tuser);
			map.put("isUpdate", "true");
			session.setAttribute("userMessage", tuser);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("isUpdate", "false");
		}
		return map;
	}

	@Override
	public void updateAvatar(Tuser tuser, HttpSession session) {
		// TODO Auto-generated method stub
		userDao.updateAvatar(tuser);
		session.setAttribute("userMessage", tuser);
	}

	@Override
	public Mytake myput(Integer userId) {
		// TODO Auto-generated method stub
		List<Texpress> list = publishDao.findmyputpublish(userId);
		List<Publish> list2 = new ArrayList<Publish>();
		for (int i = 0; i < list.size(); i++) {
			Publish publish = publishService.Tpublish_Publish(list.get(i));
			publish.setCode(list.get(i).getCode());
			list2.add(publish);
		}
		Mytake my = new Mytake();
		my.setPublish(list2);
		return my;
	}

	@Override
	public Mytake mytake(Integer userId) {
		// TODO Auto-generated method stub
		List<Texpress> list = publishDao.findmytakepublih(userId);
		List<Publish> list2 = new ArrayList<Publish>();
		for (int i = 0; i < list.size(); i++) {
			Publish publish = publishService.Tpublish_Publish(list.get(i));
			publish.setCode(list.get(i).getCode());
			publish.setLatitude(list.get(i).getLatitude());
			publish.setLongitude(list.get(i).getLongitude());
			publish.setOrderdate(String.valueOf(messageDao.findByFromNum(publish.getFromNum()).get(0).getOrderDate()));
			list2.add(publish);
		}
		List<Texpress> r = requestDao.findmytakerequest(userId);
		for (int i = 0; i < r.size(); i++) {
			Publish publish = publishService.Tpublish_Publish(r.get(i));
			publish.setCode(r.get(i).getCode());
			publish.setLatitude(r.get(i).getLatitude());
			publish.setLongitude(r.get(i).getLongitude());
			list2.add(publish);
		}
		Mytake my = new Mytake();
		my.setPublish(list2);
		return my;
	}

	@Override
	public Mytake mytaken(Integer userId) {
		// TODO Auto-generated method stub
		List<Texpress> list = publishDao.findmytakenpublish(userId);
		System.out.println("list=" + list.size());
		List<Publish> list2 = new ArrayList<Publish>();
		for (int i = 0; i < list.size(); i++) {
			Publish publish = publishService.Tpublish_Publish(list.get(i));
			publish.setOrderdate(tool.formatTime(messageDao.finddate(list.get(i).getExpressId(), 1).getOrderDate()));
			publish.setPublisheruserId(messageDao.finddate(list.get(i).getExpressId(), 1).getPassivePer().getUserId());
			publish.setPublisheruserimage(
					messageDao.finddate(list.get(i).getExpressId(), 1).getPassivePer().getImage());
			publish.setPublisherusername(messageDao.finddate(list.get(i).getExpressId(), 1).getPassivePer().getName());
			list2.add(publish);
		}
		List<Texpress> list3 = requestDao.findmytakenrequest(userId);
		System.out.println("我的被接" + list3.size());
		for (int i = 0; i < list3.size(); i++) {
			Publish publish = publishService.Tpublish_Publish(list3.get(i));
			publish.setOrderdate(tool.formatTime(messageDao.finddate(list3.get(i).getExpressId(), 2).getOrderDate()));
			list2.add(publish);
		}
		Mytake my = new Mytake();
		my.setPublish(list2);
		return my;
	}

	@Override
	public User findUser(int personId) {
		int PHsize = 0;
		int ESsize = 0;
		Tuser tuser = userDao.findUser_by_Id(personId);
		User user = new User();
		BeanUtils.copyProperties(tuser, user);
		user.setUserId(tuser.getUserId());

		List<Tmessage> list = messageDao.countPh(personId);
		if (list.size() != 0) {
			for (Tmessage tmessage : list) {
				if (tmessage.getFromNum().substring(0, 2).equals("PH"))
					PHsize++;
			}
		}
		List<Trequest> list1 = requestDao.findByRequest(personId);
		if (list1.size() != 0) {
			for (Trequest trequest : list1) {
				List<Tmessage> list2 = messageDao.findByFromNum(trequest.getFromNum());
				ESsize = ESsize + list2.size();
			}
		}
		System.out.println("PH的个数="+PHsize);
		System.out.println("ES的个数="+ESsize);
		user.setCount(PHsize + ESsize);
		return user;
	}
}
