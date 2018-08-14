package com.zust.EDP.service;

import java.util.Date;

import com.zust.EDP.entity.Tmessage;
import com.zust.EDP.entity.Tuser;

public interface WebSocketService {
	public String sendToPublisher(Tmessage message);

	public String sendToPassivePerByTrue(int id,int supportId);

	public String sendToPassivePerByFalse(int id,int supportId);

	public void saveRecord(Tuser user1, Tuser user2, String message, int state, Date date);

	public String sendToExpressSecond(String fromNumOne, String fromNumTwo, int integral,int supportId);

	public String findTrecord(int fromId, int toId);

	public boolean userCreditLevelCompareWithPRequirement(int id, String fromNum);

	public String splitTypeSix(int id);

	public String backToExpressMessage(int id);
}
