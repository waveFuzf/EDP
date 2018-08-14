package com.zust.EDP.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zust.EDP.dao.MessageDao;
import com.zust.EDP.dao.UserDao;
import com.zust.EDP.dto.Message;
import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Tmessage;
import com.zust.EDP.entity.Tuser;
import com.zust.EDP.service.MessageService;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private MessageDao messageDao;

	@Override
	public Tmessage Message_Tmessage(Message message) {
		// TODO Auto-generated method stub
		Tmessage message2 = new Tmessage();
		message2.setFromNum(message.getFromNum());
		message2.setMessageId(message.getMessageId());
		message2.setMsgType(message.getMsgType());
		message2.setPassivePer(userDao.findUser_by_Id(message.getPassivePeruserid()));
		return message2;
	}

	@Override
	public Message Tmessage_Message(Tmessage message) {
		// TODO Auto-generated method stub
		Message message2 = new Message();
		message2.setFromNum(message.getFromNum());
		message2.setMessageId(message.getMessageId());
		message2.setMsgType(message.getMsgType());
		message2.setPassivePeruserid(message.getPassivePer().getUserId());
		message2.setPassivePerusername(message.getPassivePer().getName());
		return message2;
	}

	@Override
	public List<Message> findmessage(Integer userId) {
		// TODO Auto-generated method stub
		Tuser tuser = userDao.findUser_by_Id(userId);
	    List<Tmessage> list = messageDao.findmessage(userId,"PH");
	    System.out.println(""+list.size());
		List<Message> list2 = new ArrayList<Message>();
		for(int i=0;i<list.size();i++) {
			System.out.println("I="+i);
			Tmessage message = list.get(i);
			Tuser u = messageDao.findpubuser(message.getMessageId());
			Texpress e = messageDao.findexpress(message.getFromNum());
			Message message2 = new Message();
			message2 =Tmessage_Message(message);
			message2.setPassivePeruserid(tuser.getUserId());
			message2.setPassivePerusername(tuser.getName());
			message2.setPubuserid(u.getUserId());
			message2.setPubuserimage(u.getImage());
			message2.setPubusername(u.getName());
			message2.setAddress(e.getAddress());
            message2.setCode(e.getCode());
            message2.setCompany(e.getCompany());
            message2.setExpressId(e.getExpressId());
            message2.setSize(e.getSize());
            message2.setType(e.getType());
            message2.setTip(e.getTip());
			list2.add(message2);
			System.out.println("messageservicer1 ok");
		}
		List<Map<String,Object>> list4 = messageDao.findmessageES(userId);
		System.out.println("messageservicer2"+list4.size());
		for(int i=0;i<list4.size();i++) { 
			Map<String, Object> map = list4.get(i);
			Tmessage message = (Tmessage) map.get("m");
			Texpress e = (Texpress) map.get("e");
			Tuser u = messageDao.findpubuser(message.getMessageId());
			Message message2 = new Message();
			message2 =Tmessage_Message(message);
			message2.setPassivePeruserid(tuser.getUserId());
			message2.setPassivePerusername(tuser.getName());
			message2.setMsgType(message.getMsgType()+3);
			message2.setEfromNum(e.getPublishId().getFromNum());
			message2.setPubuserid(u.getUserId());
			message2.setPubuserimage(u.getImage());
			message2.setPubusername(u.getName());
            message2.setAddress(e.getAddress());
            message2.setCode(e.getCode());
            message2.setCompany(e.getCompany());
            message2.setExpressId(e.getExpressId());
            message2.setSize(e.getSize());
            message2.setType(e.getType());
            message2.setTip(e.getTip());
			list2.add(message2);
			System.out.println("messageservicer2 ok");
		}
		List<Tmessage> list3 = messageDao.findmessage2(userId);
		for(int i=0;i<list3.size();i++) {
			Tmessage message = list3.get(i);
			Texpress e = messageDao.findexpress(message.getFromNum());
			Message message2 = new Message();
			message2 =Tmessage_Message(message);
			message2.setPassivePeruserid(tuser.getUserId());
			message2.setPassivePerusername(tuser.getName());
			message2.setPubuserid(message.getPassivePer().getUserId());
			message2.setPubuserimage(message.getPassivePer().getImage());
			message2.setPubusername(message.getPassivePer().getName());
			message2.setAddress(e.getAddress());
            message2.setCode(e.getCode());
            message2.setCompany(e.getCompany());
            message2.setExpressId(e.getExpressId());
            message2.setSize(e.getSize());
            message2.setType(e.getType());
            message2.setTip(e.getTip());
			list2.add(message2);
			System.out.println("messageservicer3 ok");
		}
		List<Map<String,Object>> list5 = messageDao.findmessageES2(userId);
		for(int i=0;i<list5.size();i++) { 
			Map<String, Object> map = list5.get(i);
			Tmessage message = (Tmessage) map.get("m");
			Texpress e = (Texpress) map.get("e");
			Message message2 = new Message();
			message2 =Tmessage_Message(message);
			message2.setPassivePeruserid(tuser.getUserId());
			message2.setPassivePerusername(tuser.getName());
			message2.setMsgType(message.getMsgType()+3);
			message2.setEfromNum(e.getPublishId().getFromNum());
			message2.setPubuserid(message.getPassivePer().getUserId());
			message2.setPubuserimage(message.getPassivePer().getImage());
			message2.setPubusername(message.getPassivePer().getName());
            message2.setAddress(e.getAddress());
            message2.setCode(e.getCode());
            message2.setCompany(e.getCompany());
            message2.setExpressId(e.getExpressId());
            message2.setSize(e.getSize());
            message2.setType(e.getType());
            message2.setTip(e.getTip());
			list2.add(message2);
			System.out.println("messageservicer4 ok");
		}
		return list2;
	}

	
	@Override
	public void readmessage(Integer messageId) {
		// TODO Auto-generated method stub
		messageDao.updatestate(messageId);
	}



}
