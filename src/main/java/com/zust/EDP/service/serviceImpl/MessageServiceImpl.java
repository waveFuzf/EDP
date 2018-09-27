package com.zust.EDP.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zust.EDP.dao.MessageDao;
import com.zust.EDP.dao.UserDao;
import com.zust.EDP.dto.Message;
import com.zust.EDP.entity.Tmessage;
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
	public List<Tmessage> findmessage(Integer userId) {
		// TODO Auto-generated method stub
		List<Tmessage> tmessages=messageDao.findmessage(userId);

		return tmessages;
	}

	
	@Override
	public void readmessage(Integer messageId) {
		// TODO Auto-generated method stub
		messageDao.updatestate(messageId);
	}

	@Override
	public void changeState(Integer messageId, int state) {
		messageDao.changeState(messageId,state);
	}


}
