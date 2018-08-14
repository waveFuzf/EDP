package com.zust.EDP.dao;

import java.util.List;
import java.util.Map;

import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Tmessage;
import com.zust.EDP.entity.Tpublish;
import com.zust.EDP.entity.Trecord;
import com.zust.EDP.entity.Trequest;
import com.zust.EDP.entity.Tuser;

public interface WebSocketDao {
	public void save(Tmessage message);

	public Map<String, Object> findPublisherName(Tmessage message);

	public Tmessage findMessage(int id);

	public Tuser findUser(int id);

	public void changeState(Tmessage msg);

	public void changeMsgType(int msgType, int id);

	public void saveRecord(Trecord trecord);

	public Tpublish findByPfromNum(String fromNum);

	public Trequest findByRfromNum(String fromNum);

	public Texpress findExpress(Tpublish tpublish);

	public List<Trecord> findRecord(int fromId, int toId);

	public void update(int integral, String fromNum);

	public void updateIntegral(int newIntegral, int id);
	
	public Tpublish find(int supportId);
}
