package com.zust.EDP.dao;

import java.util.List;
import java.util.Set;

import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Tpublish;

public interface PublishDao {
	public void savePublish(Tpublish publish);

	public List<Texpress> findPublish(int page, String address, int limit, Integer userId);

	public Tpublish findPublish_by_Id(Integer publishId);

	public Set<Tpublish> findPublish_by_Id(List<Integer> publishId);

	public List<Texpress> find_by_id(Integer userId);

	public List<Texpress> findmytakepublih(Integer userId);

	public List<Texpress> findmyputpublish(Integer userId);

	public List<Texpress> findmytakenpublish(Integer userId);

	public void updatestate(Integer PublishId, int state);

    public List<Tpublish> findPublish_by_num(String publishNum);
}
