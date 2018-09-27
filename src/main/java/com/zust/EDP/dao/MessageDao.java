package com.zust.EDP.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Tmessage;
import com.zust.EDP.entity.Tuser;

public interface MessageDao {
	public void saveMessage(Tmessage message);

	public List<Tmessage> findmessage_by_id(Tmessage message);

	public Set<Tmessage> findmessage_by_id(List<Integer> messageId);

	public Tmessage find_by_id(Integer messageId);

	public Tuser findpubuser(Integer messageId);

	public List<Tmessage> findmessage2(Integer userId);

	public List<Tmessage> findmessage(Integer userId, String a);

	public Texpress findexpress(String fromNum);

	public List<Map<String, Object>> findmessageES(Integer userId);

	public List<Map<String, Object>> findmessageES2(Integer userId);

	public void updatestate(Integer messageId);

	public Tmessage finddate(Integer expressId, int i);
	
	public List<Tmessage> countPh(int personId);
	
	public List<Tmessage> findByFromNum(String fromNum);

    public void changeState(Integer messageId, int state);

    public List<Tmessage> findmessage(Integer userId);
}
