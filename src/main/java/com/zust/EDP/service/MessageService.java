package com.zust.EDP.service;

import java.util.List;

import com.zust.EDP.dto.Message;
import com.zust.EDP.entity.Tmessage;

import javax.persistence.criteria.CriteriaBuilder;

public interface MessageService {
	public Tmessage Message_Tmessage(Message message);

	public Message Tmessage_Message(Tmessage message);

	public List<Message> findmessage(Integer userId);

	public void readmessage(Integer messageId);

    public void changeState(Integer messageId,int state);
}
