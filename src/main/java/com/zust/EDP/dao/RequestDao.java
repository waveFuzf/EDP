package com.zust.EDP.dao;

import java.util.List;
import java.util.Set;

import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Trequest;

public interface RequestDao {
	public void saveRequest(Trequest request);

	public List<Trequest> findRequest(int page, String address, int limit);

	public Set<Trequest> findRequest_by_id(List<Integer> requestId);

	public List<Texpress> findmytakerequest(Integer userId);

	public List<Texpress> findmytakenrequest(Integer userId);
	
	public List<Trequest> findByRequest(int personId);

}
