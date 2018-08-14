package com.zust.EDP.dao;

import java.util.List;
import java.util.Set;

import com.zust.EDP.entity.Tevaluate;

public interface EvaluateDao {
	public Set<Tevaluate> findevaluate_by_id(List<Integer> evaluateId);

	public void saveTevaluate(Tevaluate evaluate);
	public List<Tevaluate> findByEvaluateId(int personId);
}
