package com.zust.EDP.dao;

import java.util.List;

import com.zust.EDP.entity.Texpress;

public interface ExpressDao {
	public void saveExpress(Texpress express);

	public List<Texpress> findexpress_by_companyandcode(String company, String code);

	public Texpress findexpress_by_id(Integer expressId);
}
