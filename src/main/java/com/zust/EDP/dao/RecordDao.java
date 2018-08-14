package com.zust.EDP.dao;

import java.util.List;
import java.util.Set;

import com.zust.EDP.entity.Trecord;

public interface RecordDao {
	public Set<Trecord> findRecord_by_recordid(List<Integer> recordId);

	public List<Trecord> find_by_userId(Integer userId);

	public List<Trecord> findrecord(Integer userId1, Integer userId2);

	public void chang_record_state(List<Trecord> list);
}
